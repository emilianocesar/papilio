package ar.edu.unicen.exa.papilio.core.as.exception;

import org.eclipse.gmt.modisco.java.ASTNode;

public class ASNoTranslatorFoundException extends ASTranslatorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASNoTranslatorFoundException(String string, ASTNode node,
			ASTranslatorExceptionLevel level) {
		super(string, node, level);
	}

}
