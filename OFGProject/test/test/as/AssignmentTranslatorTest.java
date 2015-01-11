package test.as;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class AssignmentTranslatorTest {

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
	
	/**
	 * Testea la traduccion de una asignacion cuando el lado derecho es una referencia
	 * a una variable y la variable referenciada es un FieldDeclaration
	 */
	@Test
	public void translateSingleVarAccessFieldDeclarationAssignmentTest() {
	
		//caso a testear: test.TesisTest.aMethod.leftSide = test.TesisTest.field
		
		String path = Constants.ASSIGNMENT_FIELD_DECLARATION_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASVariableAccess rightSide = (ASVariableAccess)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedLeftSideName = "test.TesisTest.aMethod.leftSide";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.TesisTest.field";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
		
	}
	
	/**
	 * Testea la traduccion de una asignacion cuando el lado derecho es una referencia
	 * a una variable y la variable referenciada es una variable loca
	 */
	@Test
	public void translateSingleVarAccessVariableDeclarationStatementAssignmentTest() {
	
		//caso a testear: test.TesisTest.aMethod.leftSide = test.TesisTest.aMethod.variable
		
		String path = Constants.ASSIGNMENT_VAR_DECLARATION_STATEMENT_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASVariableAccess rightSide = (ASVariableAccess)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedLeftSideName = "test.TesisTest.aMethod.leftSide";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.TesisTest.aMethod.variable";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}
	
	/**
	 * Testea la traduccion de una asignacion cuando el lado derecho es una referencia
	 * a una variable y la variable referenciada es un parametro formal
	 */
	@Test
	public void translateSingleVarAccessSingleVariableDeclarationAssignmentTest() {

		//caso a testear: test.TesisTest.aMethod.leftSide = test.TesisTest.aMethod.arg
		
		String path = Constants.ASSIGNMENT_SINGLE_VAR_DECLARATION_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASVariableAccess rightSide = (ASVariableAccess)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedLeftSideName = "test.TesisTest.aMethod.leftSide";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.TesisTest.aMethod.arg";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}
	
	/**
	 * Testea el caso de traduccion de un assignment cuyo lado derecho es un
	 * ConstructorInvocation
	 */
	@Test
	public void translateConstructorInvocationAssignmentTest() {
		//caso a testear: test.TesisTest.field = new Element()
		
		String path = Constants.ASSIGNMENT_CONSTRUCTOR_INVOCATION_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASConstructorInvocationStatement);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASConstructorInvocationStatement rightSide = (ASConstructorInvocationStatement)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getConstructorDeclaration());
		String expectedLeftSideName = "test.TesisTest.field";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.Element.Element.this";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}

	/**
	 * Testea el caso de traduccion de un assignment cuyo lado derecho es un
	 * MethodInvocation
	 */
	@Test
	public void translateMethodInvocationAssignmentTest() {

	//caso a testear: test.TesisTest.aMethod.leftSide = test.TesisTest.aMethod.element.getValue()
		
		String path = Constants.ASSIGNMENT_METHOD_INVOCATION_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(1);
		
		//testeo resultados
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASMethodInvocationStatement);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASMethodInvocationStatement rightSide = (ASMethodInvocationStatement)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getDeclaration());
		Assert.assertTrue(rightSide.getDeclaration().returnsObject());
		String expectedLeftSideName = "test.TesisTest.aMethod.leftSide";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.Element.getValue.return";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
		Assert.assertNotNull(rightSide.getTarget());
		String expectedTargetName = "test.TesisTest.aMethod.element";
		String actualTargetName = rightSide.getTarget().getFullyScopedName();
		Assert.assertEquals(expectedTargetName, actualTargetName);
	}
	
	/**
	 * Testea el caso de traduccion de un assignment cuyo lado izquiero no es traducible
	 */
	@Test
	public void translateNonTranslatableLeftHandSideAssignment() {
		//caso a testear: this.field = value
		
		String path = Constants.ASSIGNMENT_NON_TRANSLATABLE_LEFT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//verifico que no genera el statement
		ASProgram.INSTANCE.getStatements().isEmpty();
	}

	/**
	 * Testea el caso de traduccion de un assignment cuyo lado derecho no es
	 * ninguno de los casos contemplados
	 */
	@Test
	public void translateNonTranslatableRightHandSideAssignment() {
		//caso a testear: field = array[0]
		
		String path = Constants.ASSIGNMENT_NON_TRANSLATABLE_RIGHT_SIDE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//verifico que no genera el statement
		ASProgram.INSTANCE.getStatements().isEmpty();
	}
		
	/**
	 * Testea el caso de extraccion de un elemento de un contenedor utilizando
	 * el metodo get
	 */
	@Test
	public void translateContainerGetExtractionAssignmentTest() {
		//caso a testear: field = elements.get(0)
		//as: test.TesisTest.field = test.TesisTest.aMethod.elements
	
		String path = Constants.ASSIGNMENT_CONTAINER_GET_EXTRACTION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASVariableAccess rightSide = (ASVariableAccess)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedLeftSideName = "test.TesisTest.field";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.TesisTest.aMethod.elements";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}

	/**
	 * Testea el caso de extraccion de elementos de un contenedor utilizando un
	 * Iterator
	 */
	@Test
	public void translateIteratorNextExtractionAssignmentTest() {
		//caso a testear: field = anIteator.next()
		//as: test.TesisTest.field = test.TesisTest.aMethod.anIterator
		
		String path = Constants.ASSIGNMENT_ITERATOR_EXTRACTION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(1);
		
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASVariableAccess rightSide = (ASVariableAccess)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedLeftSideName = "test.TesisTest.field";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.TesisTest.aMethod.anIterator";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}

	/**
	 * Testea el caso de una asignacion, cuando el lado izquierdo es una
	 * variable de tipo Iterator y el lado derecho es el iterator obtenido para
	 * una coleccion
	 */
	@Test
	public void testContainerIteratorAssignment() {
		//caso a testear: anIterator = elements.iterator()
		//as: test.TesisTest.aMethod.anIterator = test.TesisTest.aMethod.elements
		
		String path = Constants.ASSIGNMENT_ITERATOR_EXTRACTION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement del asProgram
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement assignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(assignment.getLeftSide() instanceof ASVariableAccess);
		Assert.assertTrue(assignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)assignment.getLeftSide();
		ASVariableAccess rightSide = (ASVariableAccess)assignment.getRightSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedLeftSideName = "test.TesisTest.aMethod.anIterator";
		String actualLeftSideName = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		String expectedRightSideName = "test.TesisTest.aMethod.elements";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}

}
