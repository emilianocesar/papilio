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
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.ForwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.PropagationDirectionStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.FlowPropagationSetInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.ObjectIdentifierInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.ObjectIdentifierPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.PropagationStrategy;

/**
 * @author Belen Rolandi
 * Genera el modelo de objetos para distintos modelos java cargados desde un xmi
 * Crea el ofg para el programa, realiza la propagacion de id de objetos
 * y guarda el resultado en el modelo. Luego persiste el modelo obtenido
 * en un archivo en la uri indicada 
 */
public class ObjectModelGenerationTest {
	
	@Before
	public void setUp() {
	
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();
	}

 @Test	
 public void generateElibminObjectModelTest(){ 

	JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
	URI baseUri = URI.createURI(Constants.BASE_URI); 
	URI uri = URI.createFileURI(Constants.ELIBMIN_MODEL_PATH).resolve(baseUri);
	Model javaModel = discoverer.loadJavaModel(uri.toString());
	
	ASGenerator asGenerator = new ASGenerator();
	asGenerator.iterate(javaModel);
	
	OFGGenerator ofgGenerator = new OFGGenerator();
	ofgGenerator.generateOFG();
	OFGGraph elibminOFG = OFGGraph.getInstance();
	
	FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
	PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
	FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
	
	ofgObjectAlgorithm.propagate(elibminOFG);
	
	serializeModel(Constants.OBJECT_ELIBMIN_PATH, javaModel);
	
	}
 
 
 @Test	
 public void generateSequenceDiagramObjectModelTest(){ 

	JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
	URI baseUri = URI.createURI(Constants.BASE_URI); 
	URI uri = URI.createFileURI(Constants.SEQUENCE_DIAGRAM_TEST_MODEL_PATH).resolve(baseUri);
	Model javaModel = discoverer.loadJavaModel(uri.toString());
	
	ASGenerator asGenerator = new ASGenerator();
	asGenerator.iterate(javaModel);
	
	OFGGenerator ofgGenerator = new OFGGenerator();
	ofgGenerator.generateOFG();
	OFGGraph seqDiagramModelOFG = OFGGraph.getInstance();
	
	FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
	PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
	FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
	
	ofgObjectAlgorithm.propagate(seqDiagramModelOFG);
	
	serializeModel(Constants.SEQUENCE_DIAGRAM_TEST_OBJECT_MODEL_PATH, javaModel);
	
	}
 
 @Test
 public void generateBinaryTreeObjectModeltest() {
	 
	 	JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(Constants.BINARY_TREE_MODEL_PATH).resolve(baseUri);
		Model javaModel = discoverer.loadJavaModel(uri.toString());
		
		ASGenerator asGenerator = new ASGenerator();
		asGenerator.iterate(javaModel);
		
		OFGGenerator ofgGenerator = new OFGGenerator();
		ofgGenerator.generateOFG();
		OFGGraph BinaryTreeOFG = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
		PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
		FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		
		ofgObjectAlgorithm.propagate(BinaryTreeOFG);
		
		serializeModel(Constants.OBJECT_BINARY_TREE_MODEL_PATH, javaModel);
 }
 
 @Test	
 public void generateOfgProjectObjectModelTest(){ 

	JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
	URI baseUri = URI.createURI(Constants.BASE_URI); 
	URI uri = URI.createFileURI(Constants.OFGRPOJECT_MODEL_PATH).resolve(baseUri);
	Model javaModel = discoverer.loadJavaModel(uri.toString());
	
	ASGenerator asGenerator = new ASGenerator();
	asGenerator.iterate(javaModel);
	
	OFGGenerator ofgGenerator = new OFGGenerator();
	ofgGenerator.generateOFG();
	OFGGraph seqDiagramModelOFG = OFGGraph.getInstance();
	
	FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
	PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
	FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
	
	ofgObjectAlgorithm.propagate(seqDiagramModelOFG);
	
	serializeModel(Constants.OBJECT_OFGRPOJECT_MODEL_PATH, javaModel);
	
	}
 
  private void serializeModel(String targetPath, Model model){
	  
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
}
