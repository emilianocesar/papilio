package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.List;
import java.util.Map;

import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class ASMethodDeclarationProxy extends ASMethodDeclaration implements
		ASDeclarationProxy {

	private ASMethodDeclaration declaration;
	
	@Override
	public ASDeclaration getDeclaration() {
		return this.declaration;
	}

	@Override
	public void setDeclaration(ASDeclaration declaration) {
		this.declaration = (ASMethodDeclaration) declaration;
	}

	@Override
	public Boolean returnsObject() {
		return this.declaration.returnsObject();
	}

	@Override
	public void setReturnsObject(Boolean returnsObject) {
		this.declaration.setReturnsObject(returnsObject);
	}

	@Override
	public List<ASAttributeDeclaration> getFormalParameters() {
		return this.declaration.getFormalParameters();
	}

	@Override
	public void setFormalParameters(List<ASAttributeDeclaration> formalParameters) {
		this.declaration.setFormalParameters(formalParameters);
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
	public Map<String, OFGNode> getOFGNodes() {
		return this.declaration.getOFGNodes();
	}

	@Override
	public String getFullyScopedName() {
		return this.declaration.getFullyScopedName();
	}

	@Override
	public void setFullyScopedName(String fullyScopedName) {
		this.declaration.setFullyScopedName(fullyScopedName);
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
	public boolean isPrimitive() {
		return declaration.isPrimitive();
	}
}
