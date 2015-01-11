package test.ofg;

import java.util.Map;

import org.junit.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * Testea la construccion del ofg a partir de la sintaxis abstracta
 * Verifica que cada elemento de la sintaxis abstracta genere correctamente 
 * los nodos y arcos correspondientes
 */
public class OFGGenerationTest {

	private ASGenerator asGenerator = new ASGenerator();

	@Before
	public void setUp() {
	
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();
	}
	
	private Model loadModel(String path) {
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(path).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		return discoverer.loadJavaModel(uri.toString());
	}
		
	@Test
	public void testAttributeDeclarationOFGContribution() {
		
		//caso a testear: private String aField
		
		String path = Constants.OFG_GENERATION_ATTRIBUTE_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		VariableDeclarationFragment fieldNode = ((FieldDeclaration)aClass.getBodyDeclarations().get(0)).getFragments().get(0);

		//obtengo la ASDeclaration asociada al nodo
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(fieldNode);
		
		//testeo resultados
		Assert.assertTrue(declaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)declaration;
		
		Map<String,OFGNode> resultNodes = asAttribute.getOFGNodes();
		Assert.assertTrue(resultNodes.size() == 1);
		OFGNode attributeNode = resultNodes.values().iterator().next();
		String expectedId = asAttribute.getFullyScopedName();
		String actualId = attributeNode.getId();
		Assert.assertEquals(expectedId, actualId);
		Assert.assertEquals(asAttribute, attributeNode.getASElement());
		
		Assert.assertTrue(asAttribute.getOFGEdges().isEmpty());

	}
	
	@Test
	public void testConstructorDeclarationOFGContribution() {
		//caso a testear: public TesisTest(String param)
		String path = Constants.OFG_GENERATION_CONSTRUCTOR_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo para el constructor
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		ConstructorDeclaration constructorDeclarationNode = (ConstructorDeclaration)aClass.getBodyDeclarations().get(0);
		
		//obtengo la ASDeclaration asociada a la declaracion de constructor
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(constructorDeclarationNode);
		Assert.assertNotNull(declaration);
		
		//testeo resultados
		Assert.assertTrue(declaration instanceof ASConstructorDeclaration);
		ASConstructorDeclaration asConstructorDeclaration = (ASConstructorDeclaration)declaration;
		Map<String, OFGNode> resultNodes = asConstructorDeclaration.getOFGNodes();
		Assert.assertTrue(resultNodes.size() == 1);
		OFGNode constructorNode = resultNodes.values().iterator().next();
		String expectedConstructorId = "test.TesisTest.TesisTest.this";
		String actualConstructorId = constructorNode.getId();
		Assert.assertEquals(expectedConstructorId, actualConstructorId);
		Assert.assertEquals(asConstructorDeclaration, constructorNode.getASElement());
		
		//testeo que no se generan arcos
		Assert.assertTrue(asConstructorDeclaration.getOFGEdges().isEmpty());
		
	}
	
	@Test
	public void testVoidMethodDeclarationOFGContribution() {
		//caso a testear: public void aMethod()
		String path = Constants.OFG_GENERATION_VOID_METHOD_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		
		//obtengo la ASDeclaration asociada al nodo
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(methodDeclaration);
		
		Assert.assertTrue(declaration instanceof ASMethodDeclaration);
		ASMethodDeclaration asMethodDeclaration = (ASMethodDeclaration)declaration;
		Map<String, OFGNode> resultNodes = asMethodDeclaration.getOFGNodes();
		Assert.assertTrue(resultNodes.size() == 1);
		OFGNode methodDeclarationNode = resultNodes.values().iterator().next();
		String expectedMethodId = "test.TesisTest.aMethod.this";
		String actualMethodId = methodDeclarationNode.getId();
		Assert.assertEquals(expectedMethodId, actualMethodId);
		Assert.assertEquals(asMethodDeclaration, methodDeclarationNode.getASElement());
		
		//testeo que no se generan arcos
		Assert.assertTrue(asMethodDeclaration.getOFGEdges().isEmpty());
	}
	
	@Test
	public void testObjectReturnMethodDeclarationOFGContribution() {
		
		//caso a testear: public Element getElement()
		String path = Constants.OFG_GENERATION_OBJECT_RETURN_METHOD_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		
		//obtengo la ASDeclaration asociada al nodo
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(methodDeclaration);
	
		Assert.assertTrue(declaration instanceof ASMethodDeclaration);
		ASMethodDeclaration asMethodDeclaration = (ASMethodDeclaration)declaration;
		Map<String, OFGNode> resultNodes = asMethodDeclaration.getOFGNodes();
		Assert.assertTrue(resultNodes.size() == 2);
		String methodDeclarationKey = "test.TesisTest.getElement.this";
		String methodReturnKey = "test.TesisTest.getElement.return";
		Assert.assertTrue(resultNodes.containsKey(methodDeclarationKey));
		Assert.assertTrue(resultNodes.containsKey(methodReturnKey));
		
		//verifico que se genera un arco para el return statement
		Assert.assertEquals(1, asMethodDeclaration.getOFGEdges().size());
		Map<String, Object> resultEdges = asMethodDeclaration.getOFGEdges();
		String objectReturnId = "test.TesisTest.element";
		String methodReturnId = "test.TesisTest.getElement.return";
		String expectedEdgeId = objectReturnId + "," + methodReturnId;
		Assert.assertTrue(resultEdges.containsKey(expectedEdgeId));
		Assert.assertFalse(asMethodDeclaration.getReturnObjects().isEmpty());
		ASTNode expectedEdgeValue = ((ASElement)asMethodDeclaration.getReturnObjects().get(0)).getNode(); 
		Assert.assertEquals(expectedEdgeValue, resultEdges.get(expectedEdgeId));
	}
	
	@Test
	public void testAssignmentStatementVarAccessRightSideOFGContribution() {
		//caso a testear: variable = arg
		//statement(0)
		String path = Constants.OFG_GENERATION_ASSIGNMENT_VAR_ACCESS_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el ASStatement generado
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;

		String leftSideId = "test.TesisTest.aMethod.variable";
		String rightSideId = "test.TesisTest.aMethod.arg";
		String expectedEdgeId = rightSideId + "," + leftSideId;
		
		Map<String, Object> resultEdges = asAssignment.getOFGEdges();
		
		Assert.assertTrue(resultEdges.size() == 1);
		Assert.assertTrue(resultEdges.containsKey(expectedEdgeId));
		ASTNode expectedEdgeValue = asAssignment.getRightSide().getNode();
		Assert.assertEquals(expectedEdgeValue, resultEdges.get(expectedEdgeId));
		
		//testeo que no se generan nodos
		Assert.assertTrue(asAssignment.getOFGNodes().isEmpty());
	}
	
	@Test
	public void testAssignmentStatementConstructorInvocationRightSideOFGContribution() {
		//caso a testear: variable = new Element()
		//statement(0)
		String path = Constants.OFG_GENERATION_ASSIGNMENT_CONSTRUCTOR_INVOCATION_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el ASStatement generado
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;

		String leftSideId = "test.TesisTest.aMethod.variable";
		String rightSideId = "test.Element.Element.this";
		String expectedEdgeId = rightSideId + "," + leftSideId; 
		
		Map<String, Object> resultEdges = asAssignment.getOFGEdges();
		Assert.assertTrue(resultEdges.size() == 1);
		Assert.assertTrue(resultEdges.containsKey(expectedEdgeId));
		ASTNode expectedEdgeValue = asAssignment.getRightSide().getNode();
		Assert.assertEquals(expectedEdgeValue, resultEdges.get(expectedEdgeId));
		
		//testeo que no se generan nodos
		Assert.assertTrue(asAssignment.getOFGNodes().isEmpty());
	}
	
	@Test
	public void testAssignmentStatementMethodInvocationRightSideOFGContribution() {
		//caso a testear: value = element.getValue()
		//statement(0)
		String path = Constants.OFG_GENERATION_ASSIGNMENT_METHOD_INVOCATION_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el ASStatement generado
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;

		String leftSideId = "test.TesisTest.aMethod.value";
		String rightSideId = "test.Element.getValue.return";
		String expectedEdgeId = rightSideId + "," + leftSideId;
		
		Map<String, Object> resultEdges = asAssignment.getOFGEdges();
		Assert.assertEquals(2, resultEdges.size());
		Assert.assertTrue(resultEdges.containsKey(expectedEdgeId));
		ASTNode expectedEdgeValue = asAssignment.getRightSide().getNode();
		Assert.assertEquals(expectedEdgeValue, resultEdges.get(expectedEdgeId));
		
		//testeo que no se generan nodos
		Assert.assertTrue(asAssignment.getOFGNodes().isEmpty());
	}
	
	@Test
	public void testConstructorInvocationOFGContribution() {
		//caso a testear: Element element = new Element(value)
		//statement(0)
		String path = Constants.OFG_GENERATION_CONSTRUCTOR_INVOCATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el ASStatement generado
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(asAssignment.getRightSide() instanceof ASConstructorInvocationStatement);
		ASConstructorInvocationStatement asClassCreation = (ASConstructorInvocationStatement)asAssignment.getRightSide();
		
		Map<String, Object> resultEdges = asClassCreation.getOFGEdges();
		Assert.assertTrue(resultEdges.size() == 1);
		String realParamId = "test.TesisTest.aMethod.value";
		String formalParamId = "test.Element.Element.value";
		String expectedEdgeId = realParamId + "," + formalParamId;

		Assert.assertTrue(resultEdges.containsKey(expectedEdgeId));
		ASElement asArgument = (ASElement)asClassCreation.getArguments().get(0);
		ASTNode expectedEdgeValue = asArgument.getNode();
		Assert.assertEquals(expectedEdgeValue, resultEdges.get(expectedEdgeId));
		
		//testeo que no se generan nodos
		Assert.assertTrue(asClassCreation.getOFGNodes().isEmpty());
	}
	
	@Test
	public void testMethodInvocationVarAccessTargetOFGContribution() {
		//caso a testear: element.setValue(value)
		//statement(0)
	
		String path = Constants.OFG_GENERATION_VAR_ACCESS_TARGET_METHOD_INVOCATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el ASStatement generado
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Map<String, Object> resultEdges = asMethodInvocation.getOFGEdges();
		Assert.assertTrue(resultEdges.size() == 2);
		
		//testeo que se genera un arco para el target(y, m.this)
		String expectedTargetId = "test.TesisTest.aMethod.element";
		String expectedMethodId = "test.Element.setValue.this";
		String expectedTargetEdgeId = expectedTargetId + "," + expectedMethodId; 
		Assert.assertTrue(resultEdges.containsKey(expectedTargetEdgeId));
		ASTNode expectedTargetEdgeValue = asMethodInvocation.getNode();
		Assert.assertEquals(expectedTargetEdgeValue, resultEdges.get(expectedTargetEdgeId));
		
		//testeo que se genera un arco para el parametro
		String realParamId = "test.TesisTest.aMethod.value";
		String formalParamId = "test.Element.setValue.aValue";
		String expectedParamEdgeId = realParamId + "," + formalParamId;
		Assert.assertTrue(resultEdges.containsKey(expectedParamEdgeId));
		
		ASElement asArgument = (ASElement)asMethodInvocation.getArguments().get(0);
		ASTNode expectedParamEdgeValue = asArgument.getNode();
		Assert.assertEquals(expectedParamEdgeValue, resultEdges.get(expectedParamEdgeId));
		
		//testeo que no se generan nodos
		Assert.assertTrue(asMethodInvocation.getOFGNodes().isEmpty());
	}
	
	@Test
	public void testMethodInvocationThisTargetOFGContribution() {
		//caso a testear: print(value), public void print(String arg)
		//statement(0)
		
		String path = Constants.OFG_GENERATION_THIS_TARGET_METHOD_INVOCATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el ASStatement generado
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Map<String, Object> resultEdges = asMethodInvocation.getOFGEdges();
		Assert.assertEquals(2, resultEdges.size());
		
		//testeo que se genera un arco para el target(y, m.this)
		String expectedTargetId = "test.TesisTest.this";
		String expectedMethodId = "test.TesisTest.print.this";
		String expectedTargetEdgeId = expectedTargetId + "," + expectedMethodId; 
		Assert.assertTrue(resultEdges.containsKey(expectedTargetEdgeId));
		ASTNode expectedTargetEdgeValue = asMethodInvocation.getNode();
		Assert.assertEquals(expectedTargetEdgeValue, resultEdges.get(expectedTargetEdgeId));
					
		//testeo que se genera un arco para el parametro
		String realParamId = "test.TesisTest.aMethod.value";
		String formalParamId = "test.TesisTest.print.arg";
		String expectedParamEdgeId = realParamId + "," + formalParamId;
		Assert.assertTrue(resultEdges.containsKey(expectedParamEdgeId));
		
		ASElement asArgument = (ASElement)asMethodInvocation.getArguments().get(0);
		ASTNode expectedParamEdgeValue = asArgument.getNode();
		Assert.assertEquals(expectedParamEdgeValue, resultEdges.get(expectedParamEdgeId));
	}
	
}

