package test.as;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ExpressionStatement;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASNamedElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class MethodInvocationTranslatorTest {

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
	public void translateThisImplicitTargetMethodInvocationTest()
			throws ASTranslatorException {
	
		//caso a testear: test.TesisTest.aMethod.this.method()
		
		String path = Constants.METHOD_INVOCATION_THIS_IMPLICIT_TARGET_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(0);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		Assert.assertTrue(asMethodInvocation.getTarget() instanceof ASAttributeDeclaration);
		String expectedTarget ="test.TesisTest.this";
		String actualTarget = asMethodInvocation.getTarget().getFullyScopedName();
		Assert.assertEquals(expectedTarget, actualTarget);
	}
	
	@Test
	public void translateThisExplicitTargetMethodInvocationTest()
			throws ASTranslatorException {

		//caso a testear: test.TesisTest.aMethod.this.method()
		String path = Constants.METHOD_INVOCATION_THIS_EXPLICIT_TARGET_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);

		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(0);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().size() > 0);
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		
		Assert.assertTrue(asMethodInvocation.getTarget() instanceof ASAttributeDeclaration);
		String expectedTarget ="test.TesisTest.this";
		String actualTarget = asMethodInvocation.getTarget().getFullyScopedName();
		Assert.assertEquals(expectedTarget, actualTarget);
	}
	
	@Test
	public void translateVarAccessTargetMethodInvocationTest()
			throws ASTranslatorException {
		
		//caso a testear: test.TesisTest.aMethod.target.method()
		String path = Constants.METHOD_INVOCATION_VAR_ACCESS_TARGET_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(1);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		
		Assert.assertTrue(asMethodInvocation.getTarget() instanceof ASVariableAccess);
		String expectedTarget = "test.TesisTest.aMethod.target";
		String actualTarget = asMethodInvocation.getTarget().getFullyScopedName();
		Assert.assertEquals(expectedTarget, actualTarget);
	}
	
	@Test
	public void translateNonTranslatableTargetMethodInvocationTest()
			throws ASTranslatorException {

		//caso a testear: new TargetClass().method()
		String path = Constants.METHOD_INVOCATION_NON_TRANSLATABLE_TARGET_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(0);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo que no traduce la invocacion
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
	}
	
	@Test
	public void translateMethodInvocationWithNoArgumentsTest()
			throws ASTranslatorException {

		//uso el modelo con varAccessTarget
		String path = Constants.METHOD_INVOCATION_VAR_ACCESS_TARGET_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(1);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		Assert.assertTrue(asMethodInvocation.getArguments().isEmpty());
		
	}
	
	@Test
	public void translatetMethodInvocationWithSingleVarDeclarationArgumentsTest()
			throws ASTranslatorException {
		//caso a testear: test.TesisTest.aMethod.target.method(test.TesisTest.aMethod.arg)
		String path = Constants.METHOD_INVOCATION_SINGLE_VAR_DECLARATION_ARGUMENT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(1);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();

		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		
		Assert.assertTrue(asMethodInvocation.getArguments().size() == 1);
		ASNamedElement param = asMethodInvocation.getArguments().get(0);
		Assert.assertTrue(param instanceof ASVariableAccess);
		String expectedParamName = "test.TesisTest.aMethod.arg";
		Assert.assertEquals(expectedParamName, param.getFullyScopedName());
	}
	
	@Test
	public void translatetMethodInvocationWithVarDeclarationStatementArgumentsTest()
			throws ASTranslatorException {
		//caso a testear: test.TesisTest.aMethod.target.method(test.TesisTest.aMethod.variable)
		String path = Constants.METHOD_INVOCATION_VAR_DECLARATION_STATEMENT_ARGUMENT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(2);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(1);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		
		Assert.assertTrue(asMethodInvocation.getArguments().size() == 1);
		ASNamedElement param = asMethodInvocation.getArguments().get(0);
		Assert.assertTrue(param instanceof ASVariableAccess);
		String expectedParamName = "test.TesisTest.aMethod.variable";
		Assert.assertEquals(expectedParamName, param.getFullyScopedName());
	}
	
	@Test
	public void translatetMethodInvocationWithFieldDeclarationArgumentsTest()
			throws ASTranslatorException {

		//caso a testear: test.TesisTest.aMethod.target.method(test.TesisTest.field)
		String path = Constants.METHOD_INVOCATION_FIELD_DECLARATION_ARGUMENT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(1);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement asMethodInvocation = (ASMethodInvocationStatement)asStatement;
		Assert.assertNotNull(asMethodInvocation.getDeclaration());
		
		Assert.assertTrue(asMethodInvocation.getArguments().size() == 1);
		ASNamedElement param = asMethodInvocation.getArguments().get(0);
		Assert.assertTrue(param instanceof ASVariableAccess);
		String expectedParamName = "test.TesisTest.field";
		Assert.assertEquals(expectedParamName, param.getFullyScopedName());
	}
	
	@Test
	public void translatetMethodInvocationWithNonTranslatableArgumentsTest()
			throws ASTranslatorException {

		//caso a testear: target.method(array[0])
		String path = Constants.METHOD_INVOCATION_NON_TRANSLATABLE_ARGUMENT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(0);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(2);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo que no se traduce la invocacion
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().size() == 0); 
		//el statement traducido corresponde al initializer del target
		
	}

	
	@Test
	public void translateContainerAddTest() throws ASTranslatorException {
	
		//caso a testear: list.add(element) 
		//as: test.TesisTest.list = test.TesisTest.aMethod.element 
		String path = Constants.METHOD_INVOCATION_CONTAINER_ADD_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(1);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asContainerInsertion = (ASAssignmentStatement)asStatement;
		
		Assert.assertTrue(asContainerInsertion.getLeftSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)asContainerInsertion.getLeftSide();
		String expectedLeftSide = "test.TesisTest.list";
		String actualLeftSide = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSide, actualLeftSide);
		
		Assert.assertTrue(asContainerInsertion.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)asContainerInsertion.getRightSide();
		String expectedRightSide = "test.TesisTest.aMethod.element";
		String actualRightSide = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSide, actualRightSide);
	}
	
	@Test
	public void translateContainerPutTest() throws ASTranslatorException {
	
		//caso a testear: map.put(key,element) 
		//as: test.TesisTest.map = test.TesisTest.aMethod.element
		String path = Constants.METHOD_INVOCATION_CONTAINER_PUT_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration methodDeclaration = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		ExpressionStatement statement = (ExpressionStatement)methodDeclaration.getBody().getStatements().get(2);
		MethodInvocation methodInvocation = (MethodInvocation)statement.getExpression();
		
		//testeo resultados
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asContainerInsertion = (ASAssignmentStatement)asStatement;
		
		Assert.assertTrue(asContainerInsertion.getLeftSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)asContainerInsertion.getLeftSide();
		String expectedLeftSide = "test.TesisTest.map";
		String actualLeftSide = leftSide.getFullyScopedName();
		Assert.assertEquals(expectedLeftSide, actualLeftSide);
		
		Assert.assertTrue(asContainerInsertion.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)asContainerInsertion.getRightSide();
		String expectedRightSide = "test.TesisTest.aMethod.element";
		String actualRightSide = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSide, actualRightSide);
	}
	
}
