package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.EnhancedForStatement;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.FieldAccess;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.ReturnStatement;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Statement;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException.ASTranslatorExceptionLevel;

/**
 * 
 * @author Belen Rolandi
 * 
 */
public class EnhancedForStatementTranslator extends AbstractTranslator {

	/**
	 * Traduce una sentencia que contiene un enhanced for Java a su sintaxis
	 * abstracta Un enhanced for es un caso especial de iteracion sobre un
	 * contenedor. Se crea un elemento ASAssignmentStatement. Como lado
	 * izquierdo se coloca el ASElement correspondiente al parametro utilizado
	 * para iterar sobre el contenedor. Como lado derecho se asigna un elemento
	 * que representa el contenedor
	 * 
	 * @param enhancedForStatement
	 *            La sentencia enhancedFor que se desea traducir
	 * @return Un elemento ASAssignmentStatement con el resultado de traducir el
	 *         enhanced for a la sintaxis abstracta
	 * 
	 */
	public List<ASElement> translate(ASTNode node) {

		EnhancedForStatement enhancedForStatement = (EnhancedForStatement) node;
		List<ASElement> resultElements = new ArrayList<ASElement>();
		ASAssignmentStatement asAssignment = new ASAssignmentStatement();
		asAssignment.setNode(enhancedForStatement);

		// traduzco el parametro
		SingleVariableDeclaration enhancedForParameter = enhancedForStatement
				.getParameter();
		AbstractSyntaxTranslator enhancedForTranslator = this
				.getTranslatorForNode(enhancedForParameter);
		ASElement asEnhancedForParameter = enhancedForTranslator.translate(
				enhancedForParameter).get(0);

		resultElements.add(asEnhancedForParameter);
		asAssignment.setLeftSide(asEnhancedForParameter);

		// traduzco la expresion en el lado derecho
		Expression enhancedForExpression = enhancedForStatement.getExpression();

		if (enhancedForExpression instanceof SingleVariableAccess) {
			AbstractSyntaxTranslator expressionTranslator = this
					.getTranslatorForNode(enhancedForExpression);
			ASElement asCollection = expressionTranslator.translate(
					enhancedForStatement.getExpression()).get(0);
			asAssignment.setRightSide(asCollection);
			resultElements.add(asAssignment);
		} else {
			if (enhancedForExpression instanceof MethodInvocation) {
				MethodInvocation invocationExpression = (MethodInvocation) enhancedForExpression;
				MethodDeclaration methodDeclaration = (MethodDeclaration) invocationExpression
						.getMethod();
				if (!(methodDeclaration.isProxy())) {
					Type returnType = methodDeclaration.getReturnType()
							.getType();
					if (this.isCollectionType(returnType)) {
						Expression returnExpression = this
								.getReturnExpression(methodDeclaration);
						ASElement asCollection = this
								.translateReturnObject(returnExpression);
						if (asCollection != null) {
							asAssignment.setRightSide(asCollection);
							resultElements.add(asAssignment);
						}
					} else {
						ASTranslatorException exception = new ASTranslatorException(
								"Unable to translate enhancedFor expression. The object returned by the expression is not a collection",
								enhancedForExpression,
								ASTranslatorExceptionLevel.INFO);
						this.context.addError(exception);
					}
				} else {
					ASTranslatorException exception = new ASTranslatorException(
							"Unable to get the collection returned by enhancedFor expression. The collection is obtained through a proxy method",
							enhancedForExpression,
							ASTranslatorExceptionLevel.INFO);
					this.context.addError(exception);
				}
			} else {
				this.context
						.addError(new ASTranslatorException(
								"The expression on the enhanced for cannot be determined",
								enhancedForStatement,
								ASTranslatorExceptionLevel.ERROR));
			}

		}

		return resultElements;
	}

	/**
	 * Obtiene la expresion correspondiente al returnStatement del metodo pasado
	 * como parametro
	 * 
	 * @param method
	 *            La declaracion de metodo para la que se desea obtener la
	 *            expresion de retorno
	 * @return La expresion retornada por el metodo, null si el metodo no posee
	 *         un ReturnStatement
	 */
	protected Expression getReturnExpression(MethodDeclaration method) {

		if (!(method.getBody() == null)) {
			for (Statement statement : method.getBody().getStatements()) {
				if (statement instanceof ReturnStatement) {
					ReturnStatement returnStatement = (ReturnStatement) statement;
					return returnStatement.getExpression();
				}
			}

		}

		return null;
	}

	/**
	 * Realiza la traduccion del objeto retornado por un metodo a su sintaxis
	 * abstracta
	 * 
	 * @param returnObject
	 *            Objeto retornado por el metodo
	 * @return ASElement con la sintaxis abstracta para el objeto pasado como
	 *         parametro
	 * 
	 */
	protected ASElement translateReturnObject(Expression returnObject) {
		ASElement asReturnObject = null;
		if (returnObject instanceof SingleVariableAccess
				|| returnObject instanceof FieldAccess) {
			AbstractSyntaxTranslator returnObjectTranslator = this
					.getTranslatorForNode(returnObject);
			List<ASElement> result = returnObjectTranslator
					.translate(returnObject);
			// el elemento retornado fue traducido correctamente
			if (!(result.isEmpty())) {
				asReturnObject = result.get(0);
			}

		} else {
			ASTranslatorException exception = new ASTranslatorException(
					"Unable to identify the object returned by the expression. The expression is not a SingleVariableAccess",
					returnObject, ASTranslatorExceptionLevel.INFO);
			this.context.addError(exception);
		}

		return asReturnObject;
	}

}
