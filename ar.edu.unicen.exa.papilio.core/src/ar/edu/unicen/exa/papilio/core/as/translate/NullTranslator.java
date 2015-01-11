package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.Collections;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.as.Context;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class NullTranslator implements AbstractSyntaxTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		return Collections.emptyList();
	}

	@Override
	public void setContext(Context context) {

	}

	@Override
	public ASTNode getEnclosedNode(ASTNode node) {
		return node;
	}

	

}
