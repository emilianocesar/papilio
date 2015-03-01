/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.translate;


import java.util.Collections;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.FieldAccess;
import org.eclipse.gmt.modisco.java.ReturnStatement;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASNamedElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException.ASTranslatorExceptionLevel;

/**
 * @author Belen Rolandi
 *
 */
public class ReturnStatementTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
	
		ReturnStatement returnStatement = (ReturnStatement)node;
		Expression returnExpression = returnStatement.getExpression();
		
		if (returnExpression instanceof SingleVariableAccess || returnExpression instanceof FieldAccess) {
			AbstractSyntaxTranslator returnObjectTranslator = this
					.getTranslatorForNode(returnExpression);
			List<ASElement> result = returnObjectTranslator
					.translate(returnExpression);
			// el elemento retornado fue traducido correctamente
			if (!(result.isEmpty())) {
				ASNamedElement asReturnObject = (ASNamedElement)result.get(0);
				//agrego return element al contexto
				context.getReturnElements().add(asReturnObject);
			}
			
		} else {
			ASTranslatorException exception = new ASTranslatorException(
					"Unable to translate return expression. The expression is not a variable reference ",
					returnExpression, ASTranslatorExceptionLevel.INFO);
			this.context.addError(exception);
		}

		//retorno una lista vacia dado que el translator no agrega ningun elemento al asProgram
		return Collections.emptyList();
	}

}
