/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ConditionalExpression;
import org.eclipse.gmt.modisco.java.Expression;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

/**
 * 
 * @author Belen Rolandi
 *
 */
public class ConditionalExpressionTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {

		ConditionalExpression conditionalExpression = (ConditionalExpression) node;
		List<ASElement> result = new ArrayList<ASElement>();

		// traduzco expresion then
		Expression thenExpression = conditionalExpression.getThenExpression();
		AbstractSyntaxTranslator translator = this
				.getTranslatorForNode(thenExpression);
		List<ASElement> thenExpressionResult = translator
				.translate(thenExpression);
		if (!(thenExpressionResult.isEmpty())) {
			ASElement thenElement = thenExpressionResult.get(0);
			result.add(thenElement);
		}

		// traduzco expresion else
		Expression elseExpression = conditionalExpression.getElseExpression();
		translator = this.getTranslatorForNode(elseExpression);
		List<ASElement> elseExpressionResult = translator
				.translate(elseExpression);
		if (!(elseExpressionResult.isEmpty())) {
			ASElement elseElement = elseExpressionResult.get(0);
			result.add(elseElement);
		}

		return result;
	}

}
