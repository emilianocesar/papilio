package test.ofg;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.BackwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.PropagationDirectionStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.FlowPropagationSetInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.TypeCoercionInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.PropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.TypePropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class ModelPropagationTest {

	private Model javaModel;
	
	@Before
	public void setUp(){
		
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();
		OFGGraph.getInstance().removeAllNodes();
	}
	
	private void createOFG(String modelPath) {
		
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(modelPath).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(uri.toString());
		
		ASGenerator asGenerator = new ASGenerator();
		OFGGenerator ofgGenerator = new OFGGenerator();	
		asGenerator.iterate(javaModel);
		ofgGenerator.generateOFG();
	}
		
	@Test
	public void TypePropagationTest() {
		
		createOFG(Constants.ELIB_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new TypeCoercionInitializationStrategy();
		FlowPropagationSetInitializationStrategy<Type> inStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> outStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> killStrategy = new EmptyInitializationStrategy<Type>();
		
		PropagationStrategy<Type> propagationStrategy = new TypePropagationStrategy();
		((TypePropagationStrategy)propagationStrategy).setJavaModel(javaModel);
		
		PropagationDirectionStrategy directionStrategy = new BackwardPropagationStrategy();
	
		FlowPropagationAlgorithm<Type> typeAlgorithm = new FlowPropagationAlgorithm<Type>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		typeAlgorithm.propagate(ofg);
		
		serializeModel(Constants.ELIB_TYPE_PROPAGATION_MODEL_PATH, javaModel);
	}
	
	private void serializeModel(String targetPath, Model model) {
		  
		  ResourceSet resourceSet = new ResourceSetImpl();
		  resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", 
				  new XMIResourceFactoryImpl()); 
		  URI fileURI = URI.createFileURI(new File(targetPath).getAbsolutePath());
		  Resource javamodelResource = resourceSet.createResource(fileURI);
		  EList<EObject> contents = javamodelResource.getContents();
		  contents.add(model);
		  try {
			javamodelResource.save(null);
		} catch (IOException e) { 	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	
	@Test
	public void ofgTest() {
		
		createOFG(Constants.ELIB_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		OFGNode node = ofg.getNode("samples.elib.Library.borrowDocument.doc");
		Assert.assertNotNull(node);
		print(node);
	}
	
	private void print(OFGNode node) {
		for (OFGEdge edge : node.getEdges()) {
			System.out.println(edge);
			print(edge.getTargetNode());
			System.out.println();
		}
	}
}
