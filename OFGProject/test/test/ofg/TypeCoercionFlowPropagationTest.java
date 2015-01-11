package test.ofg;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.ParameterizedType;
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
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;


public class TypeCoercionFlowPropagationTest {

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
	public void typeCoercionInitializationStrategyTest(){
		
		createOFG(Constants.ELIB_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new TypeCoercionInitializationStrategy();
		Map<OFGNode, Set<Type>> initialSet = genStrategy.initializeSet(ofg);
		
		OFGNode testNode1 = ofg.getNode("samples.elib.Loan.equals.obj");
		Assert.assertNotNull(testNode1);
		Set<Type> initialSetTestNode1 = initialSet.get(testNode1);
		Assert.assertEquals(1, initialSetTestNode1.size());
		
		Iterator<Type> initialSetIterator = initialSetTestNode1.iterator();
		Type testNode1Type = initialSetIterator.next();
		String expectedTestNode1TypeName = "Loan"; 
		String actualTestNode1TypeName = testNode1Type.getName();
		Assert.assertEquals(expectedTestNode1TypeName, actualTestNode1TypeName);
		
	}
	
	@Test
	public void typeCoercionPropagationStrategyTest(){
		createOFG(Constants.ELIB_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new TypeCoercionInitializationStrategy();
		FlowPropagationSetInitializationStrategy<Type> inStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> outStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> killStrategy = new EmptyInitializationStrategy<Type>();
		PropagationStrategy<Type> propagationStrategy = new TypePropagationStrategy(javaModel);
		PropagationDirectionStrategy directionStrategy = new BackwardPropagationStrategy();
	
		FlowPropagationAlgorithm<Type> typeAlgorithm = new FlowPropagationAlgorithm<Type>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		typeAlgorithm.propagate(ofg);
		
		OFGNode testNode1 = ofg.getNode("samples.elib.Library.documents");
		Assert.assertNotNull(testNode1);
		Set<Type> out1 = typeAlgorithm.getOut().get(testNode1);
		Assert.assertEquals(1, out1.size());
		
		Iterator<Type> outSetIterator = out1.iterator();
		Type testNode1Type = outSetIterator.next();
		
		String expectedTestNode1TypeName = "Document";
		String actualTestNode1TypeName = testNode1Type.getName();
		Assert.assertEquals(expectedTestNode1TypeName, actualTestNode1TypeName);
		
		OFGNode testNode2 = ofg.getNode("samples.elib.Library.addDocument.doc");
		Assert.assertNotNull(testNode2);
		Set<Type> out2 = typeAlgorithm.getOut().get(testNode2);
		Assert.assertEquals(1, out2.size());
		
		outSetIterator = out2.iterator();
		Type testNode2Type = outSetIterator.next();
		
		String expectedTestNode2TypeName = "Document";
		String actualTestNode2TypeName = testNode2Type.getName();
		Assert.assertEquals(expectedTestNode2TypeName, actualTestNode2TypeName);
		
	}
	
	@Test
	public void TypeChangePropagationTest() {
		
		createOFG(Constants.ELIB_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new TypeCoercionInitializationStrategy();
		FlowPropagationSetInitializationStrategy<Type> inStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> outStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> killStrategy = new EmptyInitializationStrategy<Type>();
		PropagationStrategy<Type> propagationStrategy = new TypePropagationStrategy(javaModel);
		PropagationDirectionStrategy directionStrategy = new BackwardPropagationStrategy();
	
		FlowPropagationAlgorithm<Type> typeAlgorithm = new FlowPropagationAlgorithm<Type>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		typeAlgorithm.propagate(ofg);
		
		//Obtengo el nodo Library.documents del modelo
		ClassDeclaration libraryClass = (ClassDeclaration)javaModel.getCompilationUnits().get(5).getTypes().get(0);
		Assert.assertEquals("Library", libraryClass.getName());
		FieldDeclaration documentsField = (FieldDeclaration)libraryClass.getBodyDeclarations().get(0);
		Assert.assertTrue(documentsField.getType().getType() instanceof ParameterizedType);
		ParameterizedType documentsFieldType = (ParameterizedType)documentsField.getType().getType(); 
		String actualDocumentsFieldBaseTypeName = documentsFieldType.getType().getType().getName();
		String expectedDocumentsFieldBaseTypeName = "Map";
		Assert.assertEquals(expectedDocumentsFieldBaseTypeName, actualDocumentsFieldBaseTypeName);
		String actualDocumentsFieldValueParamTypeName = documentsFieldType.getTypeArguments().get(1).getType().getName();
		String expectedDocumentsFieldValueParamTypeName = "Document";
		Assert.assertEquals(expectedDocumentsFieldValueParamTypeName, actualDocumentsFieldValueParamTypeName);
		
	}
	
}
