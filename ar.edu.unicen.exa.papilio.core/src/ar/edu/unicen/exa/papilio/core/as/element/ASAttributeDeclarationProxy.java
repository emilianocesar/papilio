package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Map;

import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;


public class ASAttributeDeclarationProxy extends ASAttributeDeclaration implements ASDeclarationProxy{

	private ASAttributeDeclaration declaration;	
	
	
	@Override
	public Map<String, OFGNode> getOFGNodes() {
		return declaration.getOFGNodes();
	}

	@Override
	public String getFullyScopedName() {
		if (declaration == null) {
			return "unresolved proxy ("+ this.getNode() +")";
		}
		return declaration.getFullyScopedName();
	}

	@Override
	public void setFullyScopedName(String fullyScopedName) {
		declaration.setFullyScopedName(fullyScopedName);
	}

	@Override
	public String toString() {
		String string;
		if (declaration != null)
			string = declaration.toString();
		else
			string = "unresolved proxy ("+ this.getNode() +")";
		return string;
	}

	@Override
	public ASTNode getNode() {
		return this.node;
	}

	@Override
	public void setNode(ASTNode node) {
		this.node = node;
	}

	@Override
	public ASDeclaration getDeclaration() {
		return this.declaration;
	}

	@Override
	public void setDeclaration(ASDeclaration declaration) {
		this.declaration = (ASAttributeDeclaration) declaration;
	}
	
	@Override
	public boolean isPrimitive() {
		return declaration.isPrimitive();
	}
}
