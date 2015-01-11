package test.as.cases;

import org.apache.log4j.Logger;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;
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
public class VariableDeclarationInitializerTest {

	private Model javaModel;
	static Logger logger = Logger.getLogger(VariableDeclarationInitializerTest.class);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void arrayAccessVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_ACCESS_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_ACCESS_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void arrayCreationVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_CREATION_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_CREATION_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void arrayInitializerVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.ARRAY_INITIALIZER_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.ARRAY_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void castExpressionVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CAST_EXPRESSION_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.CAST_EXPRESSION_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void parenthesizedExpressionVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.PARENTHESIZED_EXPRESSION_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.PARENTHESIZED_EXPRESSION_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void postfixExpressionVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.POSTFIX_EXPRESSION_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.POSTFIX_EXPRESSION_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void prefixExpressionVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.PREFIX_EXPRESSION_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.PREFIX_EXPRESSION_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void superFieldAccessVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_FIELD_ACCESS_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_FIELD_ACCES_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
	
	@Test
	public void superMethodInvocationVarDeclarationInitializerTest() throws ASTranslatorException {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.VAR_DECLARATION_INITIALIZER_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_METHOD_INVOCATION_VAR_INITIALIZER_TEST_METHOD);
		VariableDeclarationStatement varDeclaration = ((VariableDeclarationStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_METHOD_INVOCATION_INITIALIZER_DECLARATION_STATEMENT));
		
		thrown.expect(ASTranslatorException.class);
		
		Expression initializer = varDeclaration.getFragments().get(0).getInitializer();
		thrown.expectMessage("Unable to translate initialization statement: cannot find a translator for initializer expression: " + initializer.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(varDeclaration);
		translator.translate(varDeclaration);
	}
}
