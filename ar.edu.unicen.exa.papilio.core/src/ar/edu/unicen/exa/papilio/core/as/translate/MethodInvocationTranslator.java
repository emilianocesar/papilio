package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ClassInstanceCreation;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.FieldAccess;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.ThisExpression;

import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASEmptyElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASNamedElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException.ASTranslatorExceptionLevel;

public class MethodInvocationTranslator extends AbstractTranslator {

	/**
	 * Traduce un elemento de tipo MethodInvocation a su sintaxis abstracta Se
	 * genera un elemento de tipo ASMethodInvocation colocando una referencia al
	 * ASElement que contiene la declaracion del metodo La traduccion de una
	 * invocacion consiste en la traduccion de la expresion target y la
	 * traduccion de la lista de parametros reales
	 * 
	 * @param node
	 *            El nodo MethodInvocation a traducir
	 * @return Una lista con el elemento resultante de traducir la invocacion a
	 *         su sintaxis abstracta
	 * 
	 */
	public List<ASElement> translate(ASTNode node) {

		MethodInvocation invocationNode = (MethodInvocation) node;

		if (invocationNode.getMethod() == null)
			// modisco permite una invocacion sin una declaracion asociada
			// si la invocacion no tiene un metodo asociado retorno
			return Collections.emptyList();

		List<ASElement> resultElements = new ArrayList<ASElement>();

		if (isContainerInsertion(invocationNode)) {

			ASElement containerInsertion = translateContainerInsertion(invocationNode);
			if (containerInsertion != null) {
				resultElements.add(containerInsertion);
			}

		} else if (isContainerIteratorInvocation(invocationNode)
				|| isContainerExtractionInvocation(invocationNode)) {

			ASElement asElement = translateContainerMethodInvocation(invocationNode);
			if (asElement != null) {
				resultElements.add(asElement);
			}

		} else {

			ASMethodInvocationStatement asInvocation = new ASMethodInvocationStatement();
			asInvocation.setNode(node);
			ASMethodDeclaration methodDeclaration = (ASMethodDeclaration) this.context
					.getDeclaration(invocationNode.getMethod());
			if (methodDeclaration == null) {
				methodDeclaration = (ASMethodDeclaration) this.context
						.addUndeclaredMethod(invocationNode.getMethod());
			}
			asInvocation.setDeclaration(methodDeclaration);

			ASNamedElement asTarget = (ASNamedElement) this
					.translateTarget(invocationNode);
			asInvocation.setTarget(asTarget);

			for (Expression argument : invocationNode.getArguments()) {
				AbstractSyntaxTranslator argumentTranslator = this
						.getTranslatorForNode(argument);
				List<ASElement> result = argumentTranslator.translate(argument);
				if (result.isEmpty()) {
					// agrego un elemento vacio para conservar el lugar
					asInvocation.getArguments().add(new ASEmptyElement());
					this.context.addError(new ASTranslatorException(
							"Argument not translatable "
									+ argument.getClass().getSimpleName()
									+ ". The element was ignored", argument,
							ASTranslatorExceptionLevel.INFO));
				} else {
					ASNamedElement asArgument = (ASNamedElement) result.get(0);
					asInvocation.getArguments().add(asArgument);
				}
			}

			resultElements.add(asInvocation);
		}

		return resultElements;
	}

	/**
	 * Traduce a la sintaxis abstracta el target de la invocacion a un metodo Si
	 * el target es this, implicito o explicito genera un ASAttributeDeclaration
	 * correspondiente al objeto this (clase actual) Si el target es una
	 * referencia a un objeto, traduce esta referencia invocando al translator
	 * correspondiente
	 * 
	 * @param targetNode
	 *            El target de la invocacion que se desea traducir
	 * @return El ASElement correspondiente a la traduccion del target pasado
	 *         como parametro
	 * 
	 */
	private ASElement translateTarget(MethodInvocation invocationNode) {
		Expression targetNode = invocationNode.getExpression();
		ASElement target = null;
		if (targetNode == null || targetNode instanceof ThisExpression) {

			target = translateThisTarget(invocationNode);

		} else {
			AbstractSyntaxTranslator targetTranslator = this
					.getTranslatorForNode(targetNode);
			ASTNode enclosedNode = targetTranslator.getEnclosedNode(targetNode);
			// solo traduzco el target si es un SingleVariableAccess o
			// FieldAccess o
			// es un nodo que contiene uno de estos elementos
			if (enclosedNode instanceof SingleVariableAccess
					|| enclosedNode instanceof FieldAccess) {
				List<ASElement> result = targetTranslator.translate(targetNode);
				if (!(result.isEmpty())) {
					target = result.get(0);
				}
			}
		}

		if (target == null) {
			target = new ASEmptyElement();
		}

		return target;
	}

	/**
	 * Traduce el target this de un MethodInvocation Genera un ASVAriableAccess
	 * conteniendo una referencia al AttributeDeclaration correspondiente al
	 * objeto this Si el atributo para el objeto this no fue creado, lo crea.
	 * 
	 * @return Un ASVariableAccess para el objeto this
	 */
	private ASElement translateThisTarget(MethodInvocation invocationNode) {

		ASVariableAccess thisInstance = new ASVariableAccess();
		String thisExpressionName = this.getThisExpressionName();

		// Verifico si existe el atributo para el objeto this
		ASAttributeDeclaration thisInstanceDeclaration = (ASAttributeDeclaration) this.context
				.getDeclaration(thisExpressionName.toString());

		if (thisInstanceDeclaration == null) {
			// Creo la declaracion para el objeto this
			thisInstanceDeclaration = new ASAttributeDeclaration();
			thisInstanceDeclaration.setFullyScopedName(thisExpressionName);
			thisInstanceDeclaration.setThisTarget(true);
			// le seteo como nodo la clase actual
			thisInstanceDeclaration.setNode(context
					.getCurrentClassDeclaration());
			this.context.addElement(thisInstanceDeclaration);
		}

		thisInstance.setVariableDeclaration(thisInstanceDeclaration);
		return thisInstance;
	}

	/**
	 * Traduce a la sintaxis abstracta la insercion de un elemento en un
	 * contenedor Se genera un elemento ASAssignmentStatement que contiene como
	 * lado izquierdo un ASVariableAccess con una referencia a la coleccion,
	 * como lado derecho se coloca otro elemento ASVariableAccess referenciando
	 * el elemento insertado
	 * 
	 * @param methodInvocation
	 *            Invocaci�n a un m�todo de insercion en un contenedor
	 * @return Una lista con el elemento ASAssignmentStatement resultante
	 * 
	 */
	private ASAssignmentStatement translateContainerInsertion(
			MethodInvocation methodInvocation) {

		// Obtengo la coleccion (target de la invocacion)
		SingleVariableAccess collectionVariable = (SingleVariableAccess) methodInvocation
				.getExpression();
		// obtengo un asElement con una referencia a la declaracion de la
		// coleccion
		AbstractSyntaxTranslator varAccessTranslator = this
				.getTranslatorForNode(collectionVariable);
		ASVariableAccess collectionVarReference = (ASVariableAccess) varAccessTranslator
				.translate(collectionVariable).get(0);

		// Obtengo el objeto insertado
		int varIndex = methodInvocation.getMethod().getName().equals("add") ? 0
				: 1;
		Expression argument = methodInvocation.getArguments().get(varIndex);

		AbstractSyntaxTranslator argumentTranslator = this
				.getTranslatorForNode(argument);

		ASTNode enclosedNode = argumentTranslator.getEnclosedNode(argument);
		if (enclosedNode instanceof SingleVariableAccess
				|| enclosedNode instanceof ClassInstanceCreation
				|| enclosedNode instanceof MethodInvocation) {
			// Traduzco los casos en que el nodo insertado es un
			// SingleVariableAccess,
			// ClassInstanceCreation o MethodInvocation
			// o bien es un nodo que contiene un elemento de ste tipo

			ASElement insertedElementReference = argumentTranslator.translate(
					argument).get(0);

			ASAssignmentStatement assignment = new ASAssignmentStatement();
			assignment.setLeftSide(collectionVarReference);
			assignment.setRightSide(insertedElementReference);
			assignment.setNode(methodInvocation);
			return assignment;

		} else {
			this.context.addError(new ASTranslatorException(
					"Insertion method argument not translatable "
							+ argument.getClass().getSimpleName()
							+ ". The insertion statement was ignored",
					argument, ASTranslatorExceptionLevel.INFO));
			return null;
		}
	}

	/**
	 * Realiza la traduccion de una invocacion de metodo cuando la invocacion
	 * corresponde a un metodo de obtencion de un iterador para una coleccion o
	 * cuando corresponde a la extraccion de un elemento, ya sea mediante el
	 * metodo get o mediante el metodo next de un iterator En todos los casos la
	 * traduccion de la invocacion se reduce a la traduccion del target, es
	 * decir de la coleccion
	 * 
	 * @param node
	 *            El nodo que contiene la invocacion a traducir
	 * @return Un ASElement con el resultado de traducir la invocacion a la
	 *         sintaxis abstracta
	 * 
	 */
	private ASElement translateContainerMethodInvocation(MethodInvocation node) {
		// traduzco la coleccion (lado derecho)
		Expression targetExpression = node.getExpression();
		ASElement asTargetVariable = null;
		if (targetExpression instanceof SingleVariableAccess) {
			AbstractSyntaxTranslator translator = this
					.getTranslatorForNode(targetExpression);
			asTargetVariable = translator.translate(targetExpression).get(0);
			asTargetVariable.setNode(targetExpression);
		} else {
			ASTranslatorException exception = new ASTranslatorException(
					"Unable to translate target collection expression. Cannot find a translator for expression ["
							+ targetExpression + "]");
			exception.setNode(node);
			this.context.addError(exception);
		}
		return asTargetVariable;
	}

}
