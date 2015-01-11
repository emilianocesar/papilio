package test.as.cases;

import org.apache.log4j.Logger;
import org.eclipse.gmt.modisco.java.Assignment;
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
public class InvocationTargetTest {

	private Model javaModel;
	static Logger logger = Logger.getLogger(InvocationTargetTest.class);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void arrayAccessTargetTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_TARGET_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_ACCESS_TARGET_TEST_METHOD);
		Expression assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_ACCESS_TARGET_ASSIGNMENT)).getExpression();
		
		Assignment assignment = (Assignment)assignmentExpression;
		MethodInvocation invocation = (MethodInvocation)assignment.getRightHandSide();
		
		thrown.expect(ASTranslatorException.class);

		Expression target = invocation.getExpression();
		thrown.expectMessage("Unable to translate invocation target " + target.getClass().getSimpleName() + ": target expression not supported");
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	} 
	
	@Test
	public void parenthesizedExpressionTargetTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_TARGET_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.PARENTHESIZED_EXPRESSION_TARGET_TEST_METHOD);
		Expression assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.PARENTHESIZED_EXPRESSION_TARGET_ASSIGNMENT)).getExpression();
		Assignment assignment = (Assignment)assignmentExpression;
		MethodInvocation invocation = (MethodInvocation)assignment.getRightHandSide();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression target = invocation.getExpression();
		thrown.expectMessage("Unable to translate invocation target " + target.getClass().getSimpleName() + ": target expression not supported");
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void classCreationTargetTest() throws ASTranslatorException{

		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_TARGET_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CLASS_CREATION_TARGET_TEST_METHOD);
		Expression assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.CLASS_CREATION_TARGET_ASSIGNMENT)).getExpression();
		Assignment assignment = (Assignment)assignmentExpression;
		MethodInvocation invocation = (MethodInvocation)assignment.getRightHandSide();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression target = invocation.getExpression();
		thrown.expectMessage("Unable to translate invocation target " + target.getClass().getSimpleName() + ": target expression not supported");
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void methodInvocationTargetTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_TARGET_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.METHOD_INVOCATION_TARGET_TEST_METHOD);
		Expression assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.METHOD_INVOCATION_TARGET_ASSIGNMENT)).getExpression();
		Assignment assignment = (Assignment)assignmentExpression;
		MethodInvocation invocation = (MethodInvocation)assignment.getRightHandSide();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression target = invocation.getExpression();
		thrown.expectMessage("Unable to translate invocation target " + target.getClass().getSimpleName() + ": target expression not supported");
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void superFieldAccessTargetTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_TARGET_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_FIELD_ACCESS_TARGET_TEST_METHOD);
		Expression assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_FIELD_ACCESS_TARGET_ASSIGNMENT)).getExpression();
		Assignment assignment = (Assignment)assignmentExpression;
		MethodInvocation invocation = (MethodInvocation)assignment.getRightHandSide();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression target = invocation.getExpression();
		thrown.expectMessage("Unable to translate invocation target " + target.getClass().getSimpleName() + ": target expression not supported");
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
	@Test
	public void superMethodInvocationTargetTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.INVOCATION_TARGET_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_METHOD_INVOCATION_TARGET_TEST_METHOD);
		Expression assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_METHOD_INVOCATION_TARGET_ASSIGNMENT)).getExpression();
		Assignment assignment = (Assignment)assignmentExpression;
		MethodInvocation invocation = (MethodInvocation)assignment.getRightHandSide();
		
		thrown.expect(ASTranslatorException.class);
		
		Expression target = invocation.getExpression();
		thrown.expectMessage("Unable to translate invocation target " + target.getClass().getSimpleName() + ": target expression not supported");
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(invocation);
		translator.translate(invocation);
	}
	
}
