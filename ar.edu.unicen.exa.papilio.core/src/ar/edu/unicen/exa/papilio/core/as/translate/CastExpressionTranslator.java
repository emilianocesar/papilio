package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.CastExpression;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class CastExpressionTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		
		CastExpression castExpression = (CastExpression) node;
		Expression expression = castExpression.getExpression();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(expression);
		List<ASElement> result = translator.translate(expression);
		
		//asigno coercion de tipo
		if (!(result.isEmpty()) && castExpression.getType() != null) {
			Type type = castExpression.getType().getType();
			for (ASElement elem : result) {
				elem.setTypeCoercion(type);
			}
		}
		return result;
	}

	@Override
	public ASTNode getEnclosedNode(ASTNode node) {
		Expression expression = ((CastExpression) node).getExpression();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(expression);
		return translator.getEnclosedNode(expression);
	}

}
