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
public class AssignmentLeftSideTest {

	private Model javaModel;
	static Logger logger = Logger.getLogger(AssignmentLeftSideTest.class);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void arrayAccessLeftSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_LEFT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_ACCESS_LEFT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_ACCESS_LEFT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		thrown.expectMessage("The left side is not a SingleVariableAccess instead " + assignment.getLeftHandSide().getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);
	}
	
	@Test
	public void superFieldAccessLeftSideTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.ASSIGNMENT_LEFT_SIDE_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_FIELD_ACCESS_LEFT_SIDE_TEST_METHOD);
		ExpressionStatement assignmentExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_FIELD_ACCESS_LEFT_SIDE_ASSIGNMENT));
		Assignment assignment = (Assignment)assignmentExpression.getExpression();

		thrown.expect(ASTranslatorException.class);
		thrown.expectMessage("The left side is not a SingleVariableAccess instead " + assignment.getLeftHandSide().getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(assignment);
		translator.translate(assignment);

	}
}
