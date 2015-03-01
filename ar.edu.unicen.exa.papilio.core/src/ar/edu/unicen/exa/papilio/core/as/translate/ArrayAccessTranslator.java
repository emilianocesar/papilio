/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ArrayAccess;
import org.eclipse.gmt.modisco.java.Expression;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

/**
 * @author Belen Rolandi
 *
 */
public class ArrayAccessTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
	
		ArrayAccess arrayAccess = (ArrayAccess)node;
		Expression arrayExpression = arrayAccess.getArray();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(arrayExpression);
		return translator.translate(arrayExpression);
		
	}


}
