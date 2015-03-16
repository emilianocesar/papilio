package ar.edu.unicen.exa.papilio.core.main;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;

import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.atl.ATLResources;
import ar.edu.unicen.exa.papilio.core.atl.ATLTransformation;
import ar.edu.unicen.exa.papilio.core.diagram.PapilioDiagram;
import ar.edu.unicen.exa.papilio.core.model.JavaModelDiscoverer;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm.FlowPropagationAlgorithmListener;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.ObjectPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.TypePropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class PapilioMain {
	
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
	private ATLResources atlResources = new ATLResources();
	private int step;

	
	public PapilioMain() {
		step = 1;
	}
	
	public IJavaProject getProject() {
		return project;
	}
	
	public void discoverJavaModelFromProject(IJavaProject project, IProgressMonitor monitor, DiscoverModelListener listener) {
		assert project != null : "You must provide a valid project to start discovering";
		assert step == 1;
		this.diagram = null;
		this.project = project;
		final JavaModelDiscoverer discoverer = new JavaModelDiscoverer();
		try {
			workingModel = discoverer.discoverModelFromProject(
					project, monitor);
			this.model = EcoreUtil.copy(workingModel);
			if (listener != null)
				listener.onModelDiscovered(model);
		} catch (DiscoveryException e) {
			listener.onModelDiscoverError(e);
		}
		step = 2;
	}
	
	public void generateASProgram(IProgressMonitor monitor, GenerateASProgramListener listener) {
		assert this.workingModel != null;
		assert step == 2;
		ASGenerator asGenerator = new ASGenerator();
		this.program = asGenerator.iterate(workingModel);
		listener.onASProgramGenerated(program);
		step = 3;
	}
	
	public void generateOFG(IProgressMonitor monitor, GenerateOFGGraphListener listener) {
		assert this.program != null;
		assert step == 3;
		OFGGenerator ofgGenerator = new OFGGenerator();	
		this.graph = ofgGenerator.generateOFG(this.program);
		listener.onOFGGenerated(this.graph);
		if (this.getDiagram().equals(PapilioDiagram.USECASES)) {
			step = 7;
		}
	}
	
	public void generateGenSet(IProgressMonitor monitor, final FlowPropagationAlgorithmStepListener listener) {
		assert step == 4;
		if (PapilioDiagram.CLASS.equals(this.getDiagram())) {
			TypePropagationAlgorithm algorithm = (TypePropagationAlgorithm) this.getDiagram().getAlgorithm();
			algorithm.initialize(this.workingModel, this.graph, new FlowPropagationAlgorithmListener<Type>() {

				@Override
				public void onAlgorithmStep(Map<OFGNode, Set<Type>> gen) {
					step = 5;
					listener.onStepPerformed(gen);
				}
				
			});
		} else if (PapilioDiagram.SEQUENCE.equals(this.getDiagram())) {
			ObjectPropagationAlgorithm algorithm = (ObjectPropagationAlgorithm) this.getDiagram().getAlgorithm();
			algorithm.initialize(this.graph, new FlowPropagationAlgorithmListener<InstanceSpecification>() {

				@Override
				public void onAlgorithmStep(
						Map<OFGNode, Set<InstanceSpecification>> gen) {
					step = 5;
					listener.onStepPerformed(gen);
				}
			
			});
		} else if (PapilioDiagram.USECASES.equals(this.getDiagram())) {
			throw new UnsupportedOperationException("The Use Case Diagram doest perform any flow propagation");
		}
		step = 5;
	}
	
	public void generateOutSet(IProgressMonitor monitor, final FlowPropagationAlgorithmStepListener listener) {
		assert step == 5;
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
		step = 6;
	}

	public void setDiagram(PapilioDiagram diagram) {
		assert this.diagram != null;
		this.diagram = diagram;
	}
	
	public PapilioDiagram getDiagram() {
		return this.diagram;
	}

	public void propagateChanges(IProgressMonitor monitor, FlowPropagationModelChangedListener listener) {
		assert step == 6;
		assert this.diagram != null;
		this.diagram.getAlgorithm().applyChanges();
		listener.onModelPropagationFinished(model, workingModel);
		step = 7;
	}
	
	public void performTransformation(IProgressMonitor monitor) {
		assert step == 7;
		assert this.atlResources != null;
		ATLTransformation transformation = new ATLTransformation(this.atlResources);
		try {
			transformation.executeTransformation(this.diagram, monitor, this.workingModel, this.project);
		} catch (ATLCoreException | IOException | CoreException e) {
			e.printStackTrace();
		}
	}
	
	public boolean finish(IProgressMonitor monitor) {
		performTransformation(monitor);
		return true;
	}

	public boolean canFinish() {
		return step == 6;
	}

	public void setATLResources(ATLResources atlResources) {
		this.atlResources = atlResources;
	}
}
