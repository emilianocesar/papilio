package test.ofg;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
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
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.ForwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.PropagationDirectionStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.AllocationTypeInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.FlowPropagationSetInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.PropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.TypePropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * Testea el algoritmo de propagacion de flujo para 
 * el caso de refinamiento de tipos de objetos instanciados
 * Verifica tanto la estrategia de inicializacion, como el algoritmo de propagacion
 * y la aplicacion de los cambios en el modelo
 */
public class TypeFlowPropagationTest {

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

	/**
	 * Testea la estrategia de inicializacion TypeInitializationStrategy
	 * El test se realiza para el programa BinaryTree 
	 */
	@Test
	public void typeInitializationStrategyTest(){
		
		createOFG(Constants.BINARY_TREE_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new AllocationTypeInitializationStrategy();
		Map<OFGNode, Set<Type>> initialSet = genStrategy.initializeSet(ofg);
		
		OFGNode testNode1 = ofg.getNode("binarytree.UniversityAdmin.students");
		OFGNode testNode2 = ofg.getNode("binarytree.UniversityAdmin.addStudent.n");
		OFGNode testNode3 = ofg.getNode("binarytree.UniversityAdmin.main.s1");
				
		Set<Type> initialSetTestNode1 = initialSet.get(testNode1);
		Set<Type> initialSetTestNode2 = initialSet.get(testNode2);
		Set<Type> initialSetTestNode3 = initialSet.get(testNode3);
	
		Iterator<Type> initialSetIterator;
		String expectedTestNode1TypeName ="BinaryTree";
		initialSetIterator = initialSetTestNode1.iterator();
		String actualTestNode1TypeName = initialSetIterator.hasNext() ? initialSetIterator.next().getName() : "";
		String expectedTestNode2TypeName = "BinaryTreeNode";
		initialSetIterator = initialSetTestNode2.iterator();
		String actualTestNode2TypeName = initialSetIterator.hasNext() ? initialSetIterator.next().getName() : "";
		String expectedTestNode3TypeName = "Student";
		initialSetIterator = initialSetTestNode3.iterator();
		String actualTestNode3TypeName = initialSetIterator.hasNext() ? initialSetIterator.next().getName() : "";
		
		Assert.assertEquals(expectedTestNode1TypeName,actualTestNode1TypeName);
		Assert.assertEquals(expectedTestNode2TypeName,actualTestNode2TypeName);
		Assert.assertEquals(expectedTestNode3TypeName,actualTestNode3TypeName);
		
	}
	
	/**
	 * Testea el algoritmo de propagacion a traves del ofg para
	 * el caso de propagacion del tipo actual asignado variables
	 * El test se realiza utilizando el programa BinaryTree
	 */
	@Test
	public void typeFlowPropagationTest(){
		
		createOFG(Constants.BINARY_TREE_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new AllocationTypeInitializationStrategy();
		FlowPropagationSetInitializationStrategy<Type> inStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> outStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> killStrategy = new EmptyInitializationStrategy<Type>();
		PropagationStrategy<Type> propagationStrategy = new TypePropagationStrategy(javaModel);
		PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
	
		FlowPropagationAlgorithm<Type> typeAlgorithm = new FlowPropagationAlgorithm<Type>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		typeAlgorithm.propagate(ofg);
		
		OFGNode testNode = ofg.getNode("binarytree.BinaryTreeNode.obj");
		Set<Type> out = typeAlgorithm.getOut().get(testNode);
		Iterator<Type> outIterator = out.iterator();
		String actualTypeName = outIterator.hasNext() ? outIterator.next().getName() : "";
		String expectedTypeName = "Student";
		Assert.assertEquals(expectedTypeName,actualTypeName);

	}
	
	/**
	 * Testea la aplicacion en el modelo de los cambios resultantes
	 * de la ejecucion del algoritmo de propagacion de flujo para
	 * el caso de propagacion de tipos de variables
	 * El test se realiza sobre el programa BinaryTree
	 */
	@Test
	public void TypeChangePropagationTest() {

		createOFG(Constants.BINARY_TREE_MODEL_PATH);
		OFGGraph ofg = OFGGraph.getInstance();
		
		FlowPropagationSetInitializationStrategy<Type> genStrategy = new AllocationTypeInitializationStrategy();
		FlowPropagationSetInitializationStrategy<Type> inStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> outStrategy = new EmptyInitializationStrategy<Type>();
		FlowPropagationSetInitializationStrategy<Type> killStrategy = new EmptyInitializationStrategy<Type>();
		PropagationStrategy<Type> propagationStrategy = new TypePropagationStrategy(javaModel);
		PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
	
		FlowPropagationAlgorithm<Type> typeAlgorithm = new FlowPropagationAlgorithm<Type>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		typeAlgorithm.propagate(ofg);
		
		OFGNode testNode = ofg.getNode("binarytree.BinaryTreeNode.obj");
		Set<Type> out = typeAlgorithm.getOut().get(testNode);
		Assert.assertEquals(1,out.size());
		Type actualOutSetType = out.iterator().next();
		
		String expectedFieldTypeName = "Student";
		String actualOutSetTypeName = actualOutSetType.getName();
		Assert.assertEquals(expectedFieldTypeName, actualOutSetTypeName);
		
		ClassDeclaration binaryTreeNodeClass = (ClassDeclaration)javaModel.getCompilationUnits().get(Constants.BINARY_TREE_NODE_COMPILATION_UNIT_INDEX).getTypes().get(0);
		FieldDeclaration objAttribute = (FieldDeclaration)binaryTreeNodeClass.getBodyDeclarations().get(Constants.BINARY_TREE_NODE_OBJ_FIELD_INDEX);
		
 		String actualFieldTypeName = objAttribute.getType().getType().getName();
		
		Assert.assertEquals(expectedFieldTypeName, actualFieldTypeName);
		
	}

}	