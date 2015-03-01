package ar.edu.unicen.exa.papilio.core.as.translate;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ArrayAccess;
import org.eclipse.gmt.modisco.java.ArrayCreation;
import org.eclipse.gmt.modisco.java.ArrayInitializer;
import org.eclipse.gmt.modisco.java.ArrayLengthAccess;
import org.eclipse.gmt.modisco.java.Assignment;
import org.eclipse.gmt.modisco.java.CastExpression;
import org.eclipse.gmt.modisco.java.ClassInstanceCreation;
import org.eclipse.gmt.modisco.java.ConditionalExpression;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.EnhancedForStatement;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.FieldAccess;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.InfixExpression;
import org.eclipse.gmt.modisco.java.InstanceofExpression;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.NullLiteral;
import org.eclipse.gmt.modisco.java.ParenthesizedExpression;
import org.eclipse.gmt.modisco.java.PostfixExpression;
import org.eclipse.gmt.modisco.java.PrefixExpression;
import org.eclipse.gmt.modisco.java.ReturnStatement;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.ThisExpression;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.TypeLiteral;
import org.eclipse.gmt.modisco.java.VariableDeclarationExpression;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;
import org.eclipse.gmt.modisco.java.emf.impl.BooleanLiteralImpl;
import org.eclipse.gmt.modisco.java.emf.impl.CharacterLiteralImpl;
import org.eclipse.gmt.modisco.java.emf.impl.NumberLiteralImpl;
import org.eclipse.gmt.modisco.java.emf.impl.StringLiteralImpl;

import ar.edu.unicen.exa.papilio.core.as.Context;
import ar.edu.unicen.exa.papilio.core.as.exception.ASNoTranslatorFoundException;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException.ASTranslatorExceptionLevel;

public enum TranslatorFactory {
	
	INSTANCE;

	private AbstractSyntaxTranslator assignmentTranslator = new AssignmentTranslator();
	private AbstractSyntaxTranslator classInstanceCreationTranslator = new ClassInstanceCreationTranslator();
	private AbstractSyntaxTranslator constructorDeclarationTranslator = new ConstructorDeclarationTranslator();
	private AbstractSyntaxTranslator enhancedForStatementTranslator = new EnhancedForStatementTranslator();
	private AbstractSyntaxTranslator fieldDeclarationTranslator = new FieldDeclarationTranslator();
	private AbstractSyntaxTranslator methodDeclarationTranslator = new MethodDeclarationTranslator();
	private AbstractSyntaxTranslator methodInvocationTranslator = new MethodInvocationTranslator();
	private AbstractSyntaxTranslator singleVariableAccessTranslator = new SingleVariableAccessTranslator();
	private AbstractSyntaxTranslator variableDeclarationStatementTranslator = new VariableDeclarationStatementTranslator();
	private AbstractSyntaxTranslator variableDeclarationTranslator = new VariableDeclarationTranslator();
	private AbstractSyntaxTranslator singleVariableDeclarationTranslator = new SingleVariableDeclarationTranslator();
	private AbstractSyntaxTranslator thisExpressionTranslator = new ThisExpressionTranslator();
	private AbstractSyntaxTranslator castExpressionTranslator = new CastExpressionTranslator();
	private AbstractSyntaxTranslator postfixExpressionTranslator = new PostfixExpressionTranslator();
	private AbstractSyntaxTranslator prefixExpressionTranslator = new PrefixExpressionTranslator();
	private AbstractSyntaxTranslator fieldAccessTranslator = new FieldAccessTranslator();
	private AbstractSyntaxTranslator parenthesizedExpressionTranslator = new ParenthesizedExpressionTranslator();
	private AbstractSyntaxTranslator conditionalExpressionTranslator = new ConditionalExpressionTranslator();
	private AbstractSyntaxTranslator arrayAccessTranslator = new ArrayAccessTranslator();
	private AbstractSyntaxTranslator abstractVariablesContainerTranslator = new AbstractVariablesContainerTranslator();
	private AbstractSyntaxTranslator returnStatementTranslator = new ReturnStatementTranslator();
	private AbstractSyntaxTranslator nullTranslator = new NullTranslator();
	/**
	 * Obtiene el traductor a sintaxis abstracta para el tipo de nodo pasado como parametro.
	 * Si no existe un traductor para ese tipo de nodo genera una excepcion 
	 * @param eObject El nodo para el que se desea obtener el traductor
	 * @return El traductor para el tipo de nodo indicado 
	 *  Excepcion indicando que no es posible hallar un traductor para el nodo
	 */
	public AbstractSyntaxTranslator getTranslator(ASTNode eObject, Context context) {
		AbstractSyntaxTranslator translator = null;
		if (eObject instanceof ClassInstanceCreation) {
			translator = classInstanceCreationTranslator;
		} else if (eObject instanceof SingleVariableAccess) {
			translator = singleVariableAccessTranslator;
		} else if (eObject instanceof FieldDeclaration) {
			translator = fieldDeclarationTranslator;
		} else if (eObject instanceof ConstructorDeclaration) {
			translator = constructorDeclarationTranslator;
		} else if (eObject instanceof MethodDeclaration) {
			translator = methodDeclarationTranslator;
		} else if (eObject instanceof VariableDeclarationStatement) {
			translator = variableDeclarationStatementTranslator;
		} else if (eObject instanceof MethodInvocation) {
			translator = methodInvocationTranslator;
		} else if (eObject instanceof Assignment) {
			translator = assignmentTranslator;
		} else if (eObject instanceof EnhancedForStatement) {
			translator = enhancedForStatementTranslator;
		} else if (eObject instanceof VariableDeclarationFragment
				|| eObject instanceof EnumConstantDeclaration) {
			translator = variableDeclarationTranslator;
		} else if(eObject instanceof SingleVariableDeclaration) {
			translator = singleVariableDeclarationTranslator;
		} else if (eObject instanceof ThisExpression) {
			translator = thisExpressionTranslator;
		} else if (eObject instanceof CastExpression) {
			translator = castExpressionTranslator;
		} else if (eObject instanceof PostfixExpression) {
			translator = postfixExpressionTranslator;
		} else if (eObject instanceof PrefixExpression) {
			translator = prefixExpressionTranslator;
		} else if (eObject instanceof FieldAccess) {
			translator = fieldAccessTranslator;
		} else if (eObject instanceof ParenthesizedExpression) {
			translator = parenthesizedExpressionTranslator;
		} else if(eObject instanceof ConditionalExpression) {
			translator = conditionalExpressionTranslator;
		} else if(eObject instanceof ArrayAccess) {
			translator = arrayAccessTranslator;
		} else if (eObject instanceof VariableDeclarationExpression) {
			translator = abstractVariablesContainerTranslator;
		}  else if (eObject instanceof ReturnStatement) {
			translator = returnStatementTranslator;
		} else if (eObject instanceof NullLiteral
				|| eObject instanceof NumberLiteralImpl
				|| eObject instanceof StringLiteralImpl
				|| eObject instanceof BooleanLiteralImpl
				|| eObject instanceof CharacterLiteralImpl
				|| eObject instanceof InfixExpression
				|| eObject instanceof InstanceofExpression
				|| eObject instanceof TypeLiteral
				|| eObject instanceof TypeAccess
				|| eObject instanceof ArrayCreation
				|| eObject instanceof ArrayInitializer
				|| eObject instanceof ArrayLengthAccess) {
			translator = nullTranslator;
		} else {
			String exceptionMsg = "Unable to translate element " + eObject.getClass().getSimpleName() + ": cannot find a translator for it";
			context.addError(new ASNoTranslatorFoundException(exceptionMsg, eObject, ASTranslatorExceptionLevel.ERROR));
			translator = nullTranslator;
		}
		return translator;
	}

}
