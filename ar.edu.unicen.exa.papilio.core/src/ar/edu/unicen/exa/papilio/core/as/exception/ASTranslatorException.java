package ar.edu.unicen.exa.papilio.core.as.exception;

import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.as.Context;

public class ASTranslatorException extends Exception {

	private static final long serialVersionUID = -952358384469973785L;
	private ASTNode node;
	private ASTranslatorExceptionLevel level;

	public ASTranslatorException(String string, ASTNode node, ASTranslatorExceptionLevel level) {
		super(string);
		this.node = node;
		this.level = level;
	}
	
	public ASTranslatorException(String string) {
		super(string);
	}

	public void setNode(ASTNode node) {
		this.node = node;
	}
	
	public ASTNode getNode() {
		return this.node;
	}
	
	public ASTranslatorExceptionLevel getLevel() {
		return level;
	}

	public void setLevel(ASTranslatorExceptionLevel level) {
		this.level = level;
	}

	
	public String getMessage(Context context) {
		StringBuilder message = new StringBuilder();
		message.append(this.getLevel().toString());
		message.append(": ");
		message.append(super.getMessage());
		message.append(" on class: ");
		message.append(context.getCurrentCompilationUnit().toString());
		message.append(" line: ");
//		message.append(context.getCurrentCompilationUnit().getL);
		return message.toString();
	}
	
	@Override
	public String toString() {
		return getLevel().toString() + " - " + getMessage();
	}


	public enum ASTranslatorExceptionLevel {
		LOG("Log"),
		INFO("Info"),
		ERROR("Error");
		
		private String name;

		ASTranslatorExceptionLevel(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
 }
