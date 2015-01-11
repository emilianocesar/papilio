package test.as;

import org.junit.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class ConstructorDeclarationTranslatorTest {

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
	public void translateMethoDeclarationWithNoParametersTest() {
		
		//caso a testear: TesisTest()
		
		String path = Constants.CONSTRUCTOR_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration)aClass.getBodyDeclarations().get(1);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(constructorDeclaration);
		
		//Verifico resultados
		
		Assert.assertTrue(asDeclaration instanceof ASConstructorDeclaration);
		ASConstructorDeclaration asConstructorDeclaration = (ASConstructorDeclaration)asDeclaration;
		
		String expectedConstructorDeclarationName = "test.TesisTest.TesisTest";
		String actualConstructorDeclarationName = asConstructorDeclaration.getFullyScopedName();
		Assert.assertEquals(expectedConstructorDeclarationName, actualConstructorDeclarationName);
	}
	
	@Test
	public void translateMethoDeclarationWithParametersTest() {
		
		//caso a testear: TesisTest(Element element)
		String path = Constants.CONSTRUCTOR_DECLARATION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration)aClass.getBodyDeclarations().get(2);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(constructorDeclaration);
		
		//Verifico resultados
		
		Assert.assertTrue(asDeclaration instanceof ASConstructorDeclaration);
		ASConstructorDeclaration asConstructorDeclaration = (ASConstructorDeclaration)asDeclaration;
		
		String expectedConstructorDeclarationName = "test.TesisTest.TesisTest";
		String actualConstructorDeclarationName = asConstructorDeclaration.getFullyScopedName();
		Assert.assertEquals(expectedConstructorDeclarationName, actualConstructorDeclarationName);
		
		Assert.assertEquals(1, asConstructorDeclaration.getFormalParameters().size());
		ASAttributeDeclaration asParam = asConstructorDeclaration.getFormalParameters().get(0);
		String expectedParamName = "test.TesisTest.TesisTest.element";
		String actualParamName = asParam.getFullyScopedName();
		Assert.assertEquals(expectedParamName, actualParamName);
	}
}
