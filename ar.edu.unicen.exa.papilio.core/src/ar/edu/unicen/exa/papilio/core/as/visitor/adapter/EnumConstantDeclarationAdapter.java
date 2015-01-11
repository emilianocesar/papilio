package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class EnumConstantDeclarationAdapter extends ASTNodeAdapter {
	
private EnumConstantDeclaration node;	
	
	public EnumConstantDeclarationAdapter(EnumConstantDeclaration node){
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    visitor.visit(node);
	    visitor.afterVisit(node);
	}

}
