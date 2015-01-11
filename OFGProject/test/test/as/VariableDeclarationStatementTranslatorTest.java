package test.as;

import org.junit.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class VariableDeclarationStatementTranslatorTest {

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
	public void translateVarDeclarationStatementWithNoInitializerTest() {
		//caso a testear: test.TesisTest.aMethod.variable
		//statement(0)
		
		String path = Constants.VAR_DECLARATION_STATEMENT_NO_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion del asProgram
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.variable";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		//chequeo que no se genero ningun statement
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
	}	
	
	@Test
	public void translateVarDeclarationStatementWithClassCreationInitializerTest() {
		//caso a testear: test.TesisTest.aMethod.variable = test.Element.Element.this
		
		String path = Constants.VAR_DECLARATION_STATEMENT_CLASS_CREATION_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.variable";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)asStatement;
	
		Assert.assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedVarName, leftSide.getFullyScopedName());
		
		Assert.assertTrue(initializer.getRightSide() instanceof ASConstructorInvocationStatement);
		ASConstructorInvocationStatement rightSide = (ASConstructorInvocationStatement)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getConstructorDeclaration());
		String expectedRightSideName = "test.Element.Element.this";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);

	}
	
	@Test
	public void translateVarDeclarationStatementWithMethodInvocationInitializerTest() {
		//caso a testear: test.TesisTest.aMethod.variable = element.getValue()
		//target: test.TesisTest.element, method: test.Element.getValue.return
		
		String path = Constants.VAR_DECLARATION_STATEMENT_METHOD_INVOCATION_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.variable";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)asStatement;
		
		Assert.assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedVarName, leftSide.getFullyScopedName());
		
		Assert.assertTrue(initializer.getRightSide() instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement rightSide = (ASMethodInvocationStatement)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getDeclaration());
		String expectedInvocationName = "test.Element.getValue.return";
		String actualInvocationName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedInvocationName, actualInvocationName);
		Assert.assertNotNull(rightSide.getTarget());
		String expectedTargetName = "test.TesisTest.element";
		String actualTargetName = rightSide.getTarget().getFullyScopedName();
		Assert.assertEquals(expectedTargetName, actualTargetName);
	}	
	
	@Test
	public void translateVarDeclarationStatementWithVarAccessInitializerTest() {

		//caso a testear: test.TesisTest.aMethod.variable = test.TesisTest.aField
		//statement(0)
		
		String path = Constants.VAR_DECLARATION_STATEMENT_VAR_ACCESS_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.variable";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedVarName, leftSide.getFullyScopedName());
		
		Assert.assertTrue(initializer.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedRightSideName = "test.TesisTest.aField";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}
	
	@Test
	public void translateVarDeclarationStatementWithNonTranslatableInitializerTest() {
		
		String path = Constants.VAR_DECLARATION_STATEMENT_NON_TRANSLATABLE_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.variable";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//compruebo que no se genero ningun statement
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
	}
	
	@Test
	public void translateVarDeclarationStatementWithIteratorAssignmentInitializerTest() {
		//caso a testear: test.TesisTest.aMethod.anIterator = tesis.TesisTest.aMethod.elements  
		
		String path = Constants.VAR_DECLARATION_STATEMENT_ITERATOR_ASSIGNMENT_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.anIterator";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedVarName, leftSide.getFullyScopedName());
		
		Assert.assertTrue(initializer.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedRightSideName = "test.TesisTest.aMethod.elements";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);

	
	}

	@Test
	public void translateVarDeclarationStatementWithGetContainerExtractionInitializerTest() {
		//caso a testear: test.TesisTest.aMethod.element = tesis.TesisTest.elements
		
		String path = Constants.VAR_DECLARATION_STATEMENT_COLLECTION_GET_EXTRACTION_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(0)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.element";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedVarName, leftSide.getFullyScopedName());
		
		Assert.assertTrue(initializer.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedRightSideName = "test.TesisTest.elements";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}
	
	@Test
	public void translateVarDeclarationStatementWithNextContainerExtractionInitializerTest() {
		//caso a testear: test.TesisTest.aMethod.element = tesis.TesisTest.aMethod.anIterator
		//statement(0)
		
		String path = Constants.VAR_DECLARATION_STATEMENT_ITERATOR_NEXT_EXTRACTION_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo a traducir
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration method = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		VariableDeclarationFragment testVar = ((VariableDeclarationStatement)method.getBody().getStatements().get(1)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration asDeclaration = ASProgram.INSTANCE.getDeclaration(testVar);
		Assert.assertTrue(asDeclaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)asDeclaration;
		Assert.assertEquals(testVar, asAttribute.getNode());
		String expectedVarName = "test.TesisTest.aMethod.element";
		String actualVarName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedVarName, actualVarName);
		
		//obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(1);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedVarName, leftSide.getFullyScopedName());
		
		Assert.assertTrue(initializer.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedRightSideName = "test.TesisTest.aMethod.anIterator";
		String actualRightSideName = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
	}

}
