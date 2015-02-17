package ar.edu.unicen.exa.papilio.core.main;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;

import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.diagram.PapilioDiagram;
import ar.edu.unicen.exa.papilio.core.model.JavaModelDiscoverer;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm.FlowPropagationAlgorithmListener;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.ObjectPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.TypePropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public enum PapilioMain {
	
	INSTANCE;

	public interface DiscoverModelListener {
		
		public void onModelDiscovered(Model model);
		
		public void onModelDiscoverError(DiscoveryException exception);
	}

	public interface GenerateASProgramListener {
		
		public void onASProgramGenerated(ASProgram program);
		
	}
	
	public interface GenerateOFGGraphListener {
		
		public void onOFGGenerated(OFGGraph ofg);
		
	}
	
	public interface FlowPropagationAlgorithmStepListener {

		public <T extends ASTNode> void onStepPerformed(Map<OFGNode, Set<T>> set);
	}
	
	public interface FlowPropagationModelChangedListener {
		
		public void onModelPropagationFinished(Model model, Model derivedModel);
		
	}

	
	private IJavaProject project;
	private Model model;
	private Model workingModel;
	private PapilioDiagram diagram;
	private ASProgram program;
	private OFGGraph graph;

	
	public IJavaProject getProject() {
		return project;
	}
	
	public void discoverJavaModelFromProject(IJavaProject project, IProgressMonitor monitor, DiscoverModelListener listener) {
		this.diagram = null;
		this.project = project;
		final JavaModelDiscoverer discoverer = new JavaModelDiscoverer();
		try {
			workingModel = discoverer.discoverModelFromProject(
					project, monitor);
			this.model = EcoreUtil.copy(workingModel);
			listener.onModelDiscovered(model);
		} catch (DiscoveryException e) {
			listener.onModelDiscoverError(e);
		}
	}
	
	public void generateASProgram(IProgressMonitor monitor, GenerateASProgramListener listener) {
		ASGenerator asGenerator = new ASGenerator();
		this.program = asGenerator.iterate(workingModel);
		listener.onASProgramGenerated(program);
	}
	
	public void generateOFG(IProgressMonitor monitor, GenerateOFGGraphListener listener) {
		OFGGenerator ofgGenerator = new OFGGenerator();	
		this.graph = ofgGenerator.generateOFG(this.program);
		listener.onOFGGenerated(this.graph);
	}
	
	public void generateGenSet(IProgressMonitor monitor, final FlowPropagationAlgorithmStepListener listener) {
		if (PapilioDiagram.CLASS.equals(this.getDiagram())) {
			TypePropagationAlgorithm algorithm = (TypePropagationAlgorithm) this.getDiagram().getAlgorithm();
			algorithm.initialize(this.workingModel, this.graph, new FlowPropagationAlgorithmListener<Type>() {

				@Override
				public void onAlgorithmStep(Map<OFGNode, Set<Type>> gen) {
					listener.onStepPerformed(gen);
				}
				
			});
		} else if (PapilioDiagram.SEQUENCE.equals(this.getDiagram())) {
			ObjectPropagationAlgorithm algorithm = (ObjectPropagationAlgorithm) this.getDiagram().getAlgorithm();
			algorithm.initialize(this.graph, new FlowPropagationAlgorithmListener<InstanceSpecification>() {

				@Override
				public void onAlgorithmStep(
						Map<OFGNode, Set<InstanceSpecification>> gen) {
					listener.onStepPerformed(gen);
				}
			
			});
		} else if (PapilioDiagram.USECASES.equals(this.getDiagram())) {
			throw new UnsupportedOperationException("The Use Case Diagram doest perform any flow propagation");
		}
	}
	
	public void generateOutSet(IProgressMonitor monitor, final FlowPropagationAlgorithmStepListener listener) {
		if (PapilioDiagram.CLASS.equals(this.getDiagram())) {
			TypePropagationAlgorithm algorithm = (TypePropagationAlgorithm) this.getDiagram().getAlgorithm();
			algorithm.propagate(this.graph, new FlowPropagationAlgorithmListener<Type>() {

				@Override
				public void onAlgorithmStep(Map<OFGNode, Set<Type>> out) {
					listener.onStepPerformed(out);
				}
				
			});
		} else if (PapilioDiagram.SEQUENCE.equals(this.getDiagram())) {
			ObjectPropagationAlgorithm algorithm = (ObjectPropagationAlgorithm) this.getDiagram().getAlgorithm();
			algorithm.propagate(this.graph, new FlowPropagationAlgorithmListener<InstanceSpecification>() {

				@Override
				public void onAlgorithmStep(
						Map<OFGNode, Set<InstanceSpecification>> out) {
					listener.onStepPerformed(out);
				}
			
			});
		} else if (PapilioDiagram.USECASES.equals(this.getDiagram())) {
			throw new UnsupportedOperationException("The Use Case Diagram doest perform any flow propagation");
		}
	}

	public void setDiagram(PapilioDiagram diagram) {
		this.diagram = diagram;
	}
	
	public PapilioDiagram getDiagram() {
		return this.diagram;
	}

	public void propagateChanges(IProgressMonitor monitor, FlowPropagationModelChangedListener listener) {
		this.getDiagram().getAlgorithm().applyChanges();
		listener.onModelPropagationFinished(model, workingModel);
	}
}
