package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.FieldAccess;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class FieldAccessTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		
		FieldAccess fieldAccess = (FieldAccess) node;
		SingleVariableAccess field = fieldAccess.getField();
		AbstractSyntaxTranslator translator = this.getTranslatorForNode(field);
		return translator.translate(field);
	}

}
