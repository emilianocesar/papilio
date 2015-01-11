package test.as.cases;

import org.apache.log4j.Logger;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.ExpressionStatement;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.translate.AbstractSyntaxTranslator;
import ar.edu.unicen.exa.papilio.core.as.translate.TranslatorFactory;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

/**
 * @author Belen Rolandi
 *
 */
public class InvocationParameterTest {

	private Model javaModel;
	static Logger logger = Logger.getLogger(InvocationParameterTest.class);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void arrayAccessParameterTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_ACCESS_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_ACCESS_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void arrayCreationParameterTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_CREATION_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_CREATION_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
				
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void castExpressionParameterTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CAST_EXPRESSION_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.CAST_EXPRESSION_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void classCreationParameterTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CLASS_CREATION_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.CLASS_CREATION_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
			
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void methodInvocationParameterTest() throws ASTranslatorException{
			
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.METHOD_INVOCATION_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.METHOD_INVOCATION_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void superFieldAccessParameterTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_FIELD_ACCESS_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_FIELD_ACCESS_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void superMethodInvocationParameterTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_PARAMETER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_METHOD_INVOCATION_PARAMETER_TEST_METHOD);
		ExpressionStatement invocationExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_METHOD_INVOCATION_PARAMETER_INVOCATION));
		MethodInvocation invocation = (MethodInvocation)invocationExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression argument = invocation.getArguments().get(0);
		thrown.expectMessage("One of the method arguments is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
}
