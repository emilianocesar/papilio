package test.ofg;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.Model;
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
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.FlowPropagationSetInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.ObjectIdentifierInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.ObjectIdentifierPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.PropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * Testea la estrategia de inicializacion para id de odbjetos y la
 * propagacion de esta informacion a traves del ofg 
 */
public class ObjectInstanceFlowPropagationTest {
	
	Model javaModel;
	
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
/**
 * Testea la estrategia de inicializacion ObjectIdentifierPropagationStrategy
 * El test se realiza para el metodo addLoan del proyecto elibmin
 */
public void objectIdInitializationStrategyTest(){

	this.createOFG(Constants.ELIBMIN_MODEL_PATH);
	OFGGraph ofg = OFGGraph.getInstance();
	
	FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
	Map<OFGNode, Set<InstanceSpecification>>gen = genStrategy.initializeSet(ofg);
	
	OFGNode testNode1 = ofg.getNode("samples.elib.Library.addLoan.loan");
	Set<InstanceSpecification> testNode1GenSet = gen.get(testNode1);
	Assert.assertEquals(testNode1GenSet.size(),0);
	
	OFGNode testNode2 = ofg.getNode("samples.elib.Library.main.me");
	Set<InstanceSpecification> testNode2GenSet = gen.get(testNode2);
	Assert.assertEquals(testNode2GenSet.size(),1);
	InstanceSpecification testnode2InitialElement = testNode2GenSet.iterator().next();
	Assert.assertEquals("User1", testnode2InitialElement.getName());
	
	OFGNode testNode3 = ofg.getNode("samples.elib.Library.this");
	Set<InstanceSpecification> testNode3GenSet = gen.get(testNode3);
	Assert.assertEquals(testNode3GenSet.size(),1);
	InstanceSpecification testnode3InitialElement = testNode3GenSet.iterator().next();
	Assert.assertEquals("Library1", testnode3InitialElement.getName());
	
}

@Test
/**
 * Testea el algoritmo de propagacion de flujo para el caso de objetos instanciados
 * El test se realiza para el modelo correpsondiente al proyecto elibmin
 */
public void objectIdPropagationTest(){
	
	 this.createOFG(Constants.ELIBMIN_MODEL_PATH);
	 OFGGraph ofg = OFGGraph.getInstance();
	
	FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
	PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
	PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
	
	FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
	
	ofgObjectAlgorithm.propagate(ofg);
	
	//testeo los resultados
	OFGNode testNode1 = ofg.getNode("samples.elib.Library.addLoan.loan");
	Set<InstanceSpecification> testNode1Out = ofgObjectAlgorithm.getOut().get(testNode1);
	Assert.assertEquals(1, testNode1Out.size());
	InstanceSpecification testNode1Instance = testNode1Out.iterator().next();
	Assert.assertEquals(testNode1Instance.getName(),"Loan1");
		
	OFGNode testNode2 = ofg.getNode("samples.elib.Loan.Loan.usr");
	Set<InstanceSpecification> testNode2Out = ofgObjectAlgorithm.getOut().get(testNode2);
	Assert.assertEquals(1, testNode2Out.size());
	InstanceSpecification testNode2Instance = testNode2Out.iterator().next();
	Assert.assertEquals(testNode2Instance.getName(), "User1");
	
	OFGNode testNode3 = ofg.getNode("samples.elib.Loan.document");
	Set<InstanceSpecification> testNode3Out = ofgObjectAlgorithm.getOut().get(testNode3);
	Assert.assertEquals(1, testNode3Out.size());
	InstanceSpecification testNode3Instance = testNode3Out.iterator().next();
	Assert.assertEquals(testNode3Instance.getName(), "Document1");
	
	OFGNode testNode4 = ofg.getNode("samples.elib.Library.addLoan.user");
	Set<InstanceSpecification> testNode4Out = ofgObjectAlgorithm.getOut().get(testNode4);
	Assert.assertEquals(1, testNode4Out.size());
	InstanceSpecification testNode4Instance = testNode4Out.iterator().next();
	Assert.assertEquals(testNode4Instance.getName(), "User1");
		
	OFGNode testNode5 = ofg.getNode("samples.elib.Library.this");
	Set<InstanceSpecification> testNode5Out = ofgObjectAlgorithm.getOut().get(testNode5);
	Assert.assertEquals(1, testNode5Out.size());
	InstanceSpecification testNode5Instance = testNode5Out.iterator().next();
	Assert.assertEquals(testNode5Instance.getName(),"Library1");
  }
}
