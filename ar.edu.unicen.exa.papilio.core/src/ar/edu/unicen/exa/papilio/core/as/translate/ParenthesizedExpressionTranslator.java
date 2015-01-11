package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.ParenthesizedExpression;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class ParenthesizedExpressionTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
	
		ParenthesizedExpression parenthesizedExpression = (ParenthesizedExpression) node;
		Expression expression = parenthesizedExpression.getExpression();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(expression);
		return translator.translate(expression);
	}

	@Override
	public ASTNode getEnclosedNode(ASTNode node) {
		Expression expression = ((ParenthesizedExpression) node).getExpression();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(expression);
		return translator.getEnclosedNode(expression);
	}

}
