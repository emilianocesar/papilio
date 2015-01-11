package test.as.cases;

import org.apache.log4j.Logger;
import org.eclipse.gmt.modisco.java.Assignment;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.ExpressionStatement;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
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
public class AssignmentRightSideTest {

	private Model javaModel;
	static Logger logger = Logger.getLogger(AssignmentRightSideTest.class);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void arrayAccessRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_ACCESS_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_ACCESS_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);

	}
	
	@Test
	public void arrayCreationRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_CREATION_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_CREATION_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void castExpressionRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CAST_EXPRESSION_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.CAST_EXPRESSION_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void parenthesizedExpressionRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.PARENTHESIZED_EXPRESSION_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.PARENTHESIZED_EXPRESSION_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void postfixExpressionRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.POSTFIX_EXPRESSION_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.PREFIX_EXPRESSION_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void prefixExpressionRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.PREFIX_EXPRESSION_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.POSTFIX_EXPRESSION_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void superFieldAccessRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_FIELD_ACCESS_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_FIELD_ACCESS_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void superMethodInvocationRightSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_RIGHT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_METHOD_INVOCATION_RIGHT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_METHOD_INVOCATION_RIGHT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
}
