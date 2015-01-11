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
public class ContainerInsertionTest {

	private Model javaModel;
	static Logger logger = Logger.getLogger(ContainerInsertionTest.class);
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void castExpressionInsertionTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.CONTAINER_INSERTION_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CAST_EXPRESSION_INSERTION_TEST_METHOD);
		ExpressionStatement insertionExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.CAST_EXPRESSION_INSERTION_STATEMENT));
		MethodInvocation insertion = (MethodInvocation)insertionExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		//obtengo el elemento a insertar (parametro del metodo de insercion)
		int varIndex = insertion.getMethod().getName().equals("add") ? 0 : 1;
		Expression argument = insertion.getArguments().get(varIndex);
		
		thrown.expectMessage("The insertion method argument is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(insertion);
		translator.translate(insertion);
	}
	
	@Test
	public void classCreationInsertionTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.CONTAINER_INSERTION_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.CLASS_CREATION_INSERTION_TEST_METHOD);
		ExpressionStatement insertionExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.CLASS_CREATION_INSERTION_STATEMENT));
		MethodInvocation insertion = (MethodInvocation)insertionExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		//obtengo el elemento a insertar (parametro del metodo de insercion)
		int varIndex = insertion.getMethod().getName().equals("add") ? 0 : 1;
		Expression argument = insertion.getArguments().get(varIndex);
		
		thrown.expectMessage("The insertion method argument is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(insertion);
		translator.translate(insertion);
		
	}
	
	@Test
	public void methodInvocationInsertionTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.CONTAINER_INSERTION_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.METHOD_INVOCATION_INSERTION_TEST_METHOD);
		ExpressionStatement insertionExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.METHOD_INVOCATION_INSERTION_STATEMENT));
		MethodInvocation insertion = (MethodInvocation)insertionExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		//obtengo el elemento a insertar (parametro del metodo de insercion)
		int varIndex = insertion.getMethod().getName().equals("add") ? 0 : 1;
		Expression argument = insertion.getArguments().get(varIndex);
		
		thrown.expectMessage("The insertion method argument is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(insertion);
		translator.translate(insertion);
	}
	
	@Test
	public void superFieldAccessInsertionTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.CONTAINER_INSERTION_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_FIELD_ACCESS_INSERTION_TEST_METHOD);
		ExpressionStatement insertionExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_FIELD_ACCESS_INSERTION_STATEMENT));
		MethodInvocation insertion = (MethodInvocation)insertionExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		//obtengo el elemento a insertar (parametro del metodo de insercion)
		int varIndex = insertion.getMethod().getName().equals("add") ? 0 : 1;
		Expression argument = insertion.getArguments().get(varIndex);
		
		thrown.expectMessage("The insertion method argument is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(insertion);
		translator.translate(insertion);
	}
	
	@Test
	public void superMethodInvocationInsertionTest() throws ASTranslatorException{
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.AS_TEST_MODEL_URI);
		CompilationUnit unit = javaModel.getCompilationUnits().get(Constants.CONTAINER_INSERTION_COMPILATION_UNIT_MODEL_INDEX);
		ClassDeclaration testClass = (ClassDeclaration) unit.getTypes().get(0);
		MethodDeclaration testMethod = (MethodDeclaration) testClass
				.getBodyDeclarations().get(Constants.SUPER_METHOD_INVOCATION_INSERTION_TEST_METHOD);
		ExpressionStatement insertionExpression = ((ExpressionStatement) testMethod
				.getBody().getStatements().get(Constants.SUPER_METHOD_INVOCATION_INSERTION_STATEMENT));
		MethodInvocation insertion = (MethodInvocation)insertionExpression.getExpression();
		
		thrown.expect(ASTranslatorException.class);
		
		//obtengo el elemento a insertar (parametro del metodo de insercion)
		int varIndex = insertion.getMethod().getName().equals("add") ? 0 : 1;
		Expression argument = insertion.getArguments().get(varIndex);
		
		thrown.expectMessage("The insertion method argument is not a SingleVariableAccess instead " + argument.getClass().getSimpleName());
		
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE.getTranslator(insertion);
		translator.translate(insertion);
	
	}
}
