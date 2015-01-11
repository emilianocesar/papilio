package test.as;

import org.junit.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASNamedElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class ConstructorInvocationTranslatorTest {

	ASGenerator asGenerator = new ASGenerator();
	
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
	public void translateVarDeclarationStatementConstructorInvocationTest() {
		
		//caso a testear Element element = new Element
		String path = Constants.CONSTRUCTOR_INVOCATION_VAR_DECLARATION_STATEMENT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement
		Assert.assertEquals(1, ASProgram.INSTANCE.getStatements().size());
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(asAssignment.getRightSide() instanceof ASConstructorInvocationStatement);
		ASConstructorInvocationStatement asClassCreation = (ASConstructorInvocationStatement)asAssignment.getRightSide();
		//compruebo que el constructor no posee argumentos
		Assert.assertTrue(asClassCreation.getArguments().isEmpty());
		//verifico el nombre
		String expectedConstructorName = "test.Element.Element.this";
		String actualConstructorName = asClassCreation.getFullyScopedName();
		Assert.assertEquals(expectedConstructorName, actualConstructorName);
	}
	
	@Test
	public void translateFieldDeclarationConstructorInvocationTest() {
		
		//caso a testear private Element element = new Element
		String path = Constants.CONSTRUCTOR_INVOCATION_FIELD_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement
		Assert.assertEquals(1, ASProgram.INSTANCE.getStatements().size());
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(asAssignment.getRightSide() instanceof ASConstructorInvocationStatement);
		ASConstructorInvocationStatement asClassCreation = (ASConstructorInvocationStatement)asAssignment.getRightSide();
		//compruebo que el constructor no posee argumentos
		Assert.assertTrue(asClassCreation.getArguments().isEmpty());
		//verifico el nombre
		String expectedConstructorName = "test.Element.Element.this";
		String actualConstructorName = asClassCreation.getFullyScopedName();
		Assert.assertEquals(expectedConstructorName, actualConstructorName);
	}
	
	@Test
	public void translateConstructorInvocationWithArgumentsTest() {
		
		//caso a testear: Element element = new Element(value)
		String path = Constants.CONSTRUCTOR_INVOCATION_WITH_ARGUMENTS_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el statement
		Assert.assertEquals(1, ASProgram.INSTANCE.getStatements().size());
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(asAssignment.getRightSide() instanceof ASConstructorInvocationStatement);
		ASConstructorInvocationStatement asClassCreation = (ASConstructorInvocationStatement)asAssignment.getRightSide();
		//compruebo que el constructor no posee argumentos
		Assert.assertEquals(1, asClassCreation.getArguments().size());
		ASNamedElement asArg = asClassCreation.getArguments().get(0);
		//verifico el nombre del constructor
		String expectedConstructorName = "test.Element.Element.this";
		String actualConstructorName = asClassCreation.getFullyScopedName();
		Assert.assertEquals(expectedConstructorName, actualConstructorName);
		//verifico el nombre del argumento
		String expectedArgName = "test.TesisTest.testMethod.value";
		String actualArgName = asArg.getFullyScopedName();
		Assert.assertEquals(expectedArgName, actualArgName);
		
	}
	
	/**
	 * Testea la creacion de un objeto de una clase de biblioteca,
	 * es decir, de una clase proxy en el modelo 
	 */
	@Test
	public void translateProxyConstructorInvocationTest() {
		
		String path = Constants.CONSTRUCTOR_INVOCATION_PROXY_INVOCATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//verifico que si la clase target del constructor es un proxy 
		//no se traduce la sentencia
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
		
	}
}
