package ar.edu.unicen.exa.papilio.core.as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclarationProxy;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclarationProxy;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclarationProxy;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclarationProxy;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;

public enum ASProgram {
	
	INSTANCE;
	
	private Map<ASTNode, ASDeclaration> declarations = new LinkedHashMap<ASTNode, ASDeclaration>();

	private Map<ASTNode, ASDeclarationProxy> undeclaredElement = new LinkedHashMap<ASTNode, ASDeclarationProxy>();

	private List<ASStatement>statements = new ArrayList<ASStatement>();	
	
	private List<ASTranslatorException> errors = new ArrayList<ASTranslatorException>();
	
	public Map<ASTNode, ASDeclaration> getDeclarations() {
		return declarations;
	}

	public List<ASStatement> getStatements() {
		return statements;
	}

	public ASDeclaration getDeclaration(ASTNode declaration){
		return declarations.get(declaration);
	}
	
	public Map<ASTNode, ASDeclarationProxy> getUndeclaredElement() {
		return undeclaredElement;
	}
	
	public ASDeclarationProxy addUndeclaredConstructor(ASTNode node) {
		ASDeclarationProxy proxy = this.undeclaredElement.get(node);
		if (proxy == null) {
			proxy = new ASConstructorDeclarationProxy();
			((ASElement)proxy).setNode(node);
		}
		this.undeclaredElement.put(node, proxy);
		return proxy;
	}
	
	public ASDeclarationProxy addUndeclaredVariable(ASTNode node) {
		ASDeclarationProxy proxy = this.undeclaredElement.get(node);
		if (proxy == null) {
			proxy = new ASAttributeDeclarationProxy();
			((ASElement)proxy).setNode(node);
		}
		this.undeclaredElement.put(node, proxy);
		return proxy;
	}
	
	public ASDeclarationProxy addUndeclaredMethod(ASTNode node) {
		ASDeclarationProxy proxy = this.undeclaredElement.get(node);
		if (proxy == null) {
			proxy = new ASMethodDeclarationProxy();
			((ASElement)proxy).setNode(node);
		}
		this.undeclaredElement.put(node, proxy);
		return proxy;
	}
	
	public void addElement(ASElement asElement) {
		if (asElement instanceof ASDeclaration) {
			ASDeclarationProxy proxy = this.undeclaredElement.get(asElement.getNode());
			if (proxy != null) {
				proxy.setDeclaration((ASDeclaration) asElement);
				this.undeclaredElement.remove(asElement.getNode());
			}
			if (!declarations.containsKey(asElement.getNode())) {
				this.declarations.put(asElement.getNode(), (ASDeclaration) asElement);
			}
		} else {
			this.statements.add((ASStatement) asElement);
		}
	}
	
	public void removeAllDeclarations() {
		if (!declarations.isEmpty()) {
			declarations = new HashMap<ASTNode, ASDeclaration>();
		}
	}
	
	public void removeAllUndeclared() {
		if (!undeclaredElement.isEmpty()) {
			undeclaredElement = new HashMap<ASTNode, ASDeclarationProxy>();
		}
	}
	
	public void removeAllStatements() {
		if (!statements.isEmpty()) {
			statements = new ArrayList<ASStatement>();
		}
	}
	
	public List<ASTranslatorException> getErrors() {
		return this.errors;
	}
	
	public ASDeclaration getDeclaration(String declarationName) {
		
		for (ASDeclaration declaration : declarations.values()) {
			
			if (declaration.getFullyScopedName().equals(declarationName))
				return declaration;
		}
		
		return null;
	}

	public void clear() {
		this.declarations = new LinkedHashMap<ASTNode, ASDeclaration>();
		this.undeclaredElement = new LinkedHashMap<ASTNode, ASDeclarationProxy>();
		this.statements = new ArrayList<ASStatement>();	
		this.errors = new ArrayList<ASTranslatorException>();
	}
}
