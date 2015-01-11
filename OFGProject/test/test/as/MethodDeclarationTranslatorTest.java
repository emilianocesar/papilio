package test.as;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclaration;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class MethodDeclarationTranslatorTest {

	private ASGenerator asGenerator = new ASGenerator();

	@Before
	public void setUp() {
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();	
	}
	
	@Test
	public void translateMethoDeclarationWithParametersTest() {
		
		//caso a testear: test.TesisTest.methodDeclarationTest(test.TesisTest.methodDeclarationTest.param1, test.TesisTest.methodDeclarationTest.param2)
		
		String path = Constants.METHOD_DECLARATION_WITH_PARAMETERS_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo para poder acceder a la declaracion en el asProgram
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(methodDeclaration);
		Assert.assertTrue(asDeclaration instanceof ASMethodDeclaration);
		ASMethodDeclaration asMethodDeclaration = (ASMethodDeclaration)asDeclaration;
		String expectedMethodName = "test.TesisTest.methodDeclarationTest";
		String actualMethodName = asMethodDeclaration.getFullyScopedName();
		Assert.assertEquals(expectedMethodName, actualMethodName);
		
		//testeo los parametros
		Assert.assertEquals(asMethodDeclaration.getFormalParameters().size(), 2);
		ASAttributeDeclaration param1 = asMethodDeclaration.getFormalParameters().get(0);
		ASAttributeDeclaration param2 = asMethodDeclaration.getFormalParameters().get(1);
		String expectedParam1 = "test.TesisTest.methodDeclarationTest.param1";
		String actualParam1 = param1.getFullyScopedName();
		String expectedParam2 = "test.TesisTest.methodDeclarationTest.param2";
		String actualParam2 = param2.getFullyScopedName();
		Assert.assertEquals(expectedParam1, actualParam1);
		Assert.assertEquals(expectedParam2, actualParam2);
	}
	
	@Test
	public void translateMethoDeclarationWithNoParametersTest() {
		
		//caso a testear: test.TesisTest.methodDeclarationTest()
		
		String path = Constants.METHOD_DECLARATION_WITH_NO_PARAMETERS_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo para poder acceder a la declaracion en el asProgram
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(methodDeclaration);
		Assert.assertTrue(asDeclaration instanceof ASMethodDeclaration);
		ASMethodDeclaration asMethodDeclaration = (ASMethodDeclaration)asDeclaration;
		String expectedMethodName = "test.TesisTest.methodDeclarationTest";
		String actualMethodName = asMethodDeclaration.getFullyScopedName();
		Assert.assertEquals(expectedMethodName, actualMethodName);
		
		//testeo que no posee parametros
		Assert.assertTrue(asMethodDeclaration.getFormalParameters().isEmpty());
	}
	
	@Test
	public void translateMethoDeclarationWithReturnObject() {

		String path = Constants.METHOD_DECLARATION_WITH_RETURN_OBJECT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo para poder acceder a la declaracion en el asProgram
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(methodDeclaration);
		Assert.assertTrue(asDeclaration instanceof ASMethodDeclaration);
		ASMethodDeclaration asMethodDeclaration = (ASMethodDeclaration)asDeclaration;

		//verifico que el metodo devuelve un objeto
		Assert.assertTrue(asMethodDeclaration.returnsObject());
		Assert.assertFalse(asMethodDeclaration.getReturnObjects().isEmpty());
		String expectedReturnObject = "test.TesisTest.returnObjectMethod.element";
		String actualReturnObject = asMethodDeclaration.getReturnObjects().get(0).getFullyScopedName();
		Assert.assertEquals(expectedReturnObject, actualReturnObject);
	}
	
	@Test
	public void translateMethoDeclarationWithNoReturnObject() {

		String path = Constants.METHOD_DECLARATION_WITH_NO_PARAMETERS_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo para poder acceder a la declaracion en el asProgram
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(methodDeclaration);
		Assert.assertTrue(asDeclaration instanceof ASMethodDeclaration);
		ASMethodDeclaration asMethodDeclaration = (ASMethodDeclaration)asDeclaration;

		//verifico que el metodo no devuelve un objeto
		Assert.assertTrue(!asMethodDeclaration.returnsObject());
	}
	
	private Model loadModel(String path) {
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(path).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		return discoverer.loadJavaModel(uri.toString());
	}
}