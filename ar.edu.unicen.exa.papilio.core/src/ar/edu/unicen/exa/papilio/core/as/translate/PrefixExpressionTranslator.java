package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.PrefixExpression;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class PrefixExpressionTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
	
		PrefixExpression prefixExpression = (PrefixExpression) node;
		Expression operand = prefixExpression.getOperand();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(operand);
		return translator.translate(operand);
	}

	@Override
	public ASTNode getEnclosedNode(ASTNode node) {
		Expression expression = ((PrefixExpression) node).getOperand();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(expression);
		return translator.getEnclosedNode(expression);
	}

}
