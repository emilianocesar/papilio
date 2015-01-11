package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Assignment;

import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class AssignmentTranslator extends AbstractTranslator {

	/**
	 * Traduce un elemento de tipo Assignment a su sintaxis abstracta. Un
	 * Assignment en un programa Java es un elemento que contiene una expresion
	 * del lado izquierdo y otra expresion en su lado derecho. Se genera un
	 * elemento ASStatement con el resultado de traducir el lado derecho a la
	 * sintaxis abstracta Los tipos de expresiones contempladas para el lado
	 * derecho son ClassInstanceCreation, MethodInvocation y
	 * SingleVariableAccess Luego se agrega como parte izquierda del statement
	 * el resultado de traducir la expresion en el lado izquierdo
	 * 
	 * @param node
	 *            El assignment a traducir
	 * @return Una lista con el elemento resultante de traducir el assignment a
	 *         su sintaxis abstracta
	 * 
	 */
	@Override
	public List<ASElement> translate(ASTNode node) {

		Assignment assignment = (Assignment) node;
		List<ASElement> resultElements = new ArrayList<ASElement>();

		// traduzco el lado izquierdo
		AbstractSyntaxTranslator translator = this
				.getTranslatorForNode(assignment.getLeftHandSide());
		ASElement leftSide = null;
		if (translator != null) {
			List<ASElement> translateResult = translator.translate(assignment.getLeftHandSide());
			if (!(translateResult.isEmpty())) {
				leftSide = translateResult.get(0);
			}
		}

		// traduzco el lado derecho
		translator = this.getTranslatorForNode(assignment.getRightHandSide());
		List<ASElement> rightSideElements = null;
		if (translator != null) {
			rightSideElements = translator.translate(assignment.getRightHandSide());
		}

		if (leftSide == null || rightSideElements.isEmpty()) {
			// si alguno de los dos elementos no es traducible, no traduzco nada
			return Collections.emptyList();
		}

		for (ASElement rightSide : rightSideElements) {
			// el for es por el caso ConditionalExpression, pero queda planteado en
			// general para contemplar cualquier caso que retorne mas de un elemento
			ASAssignmentStatement resultAssignment = new ASAssignmentStatement();
			resultAssignment.setLeftSide(leftSide);
			resultAssignment.setRightSide(rightSide);
			resultAssignment.setNode(assignment);
			resultElements.add(resultAssignment);
		}

		return resultElements;
	}

}
