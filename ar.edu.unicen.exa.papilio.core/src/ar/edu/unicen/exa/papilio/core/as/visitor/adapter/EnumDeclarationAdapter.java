package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.EnumDeclaration;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class EnumDeclarationAdapter extends ASTNodeAdapter {

	private EnumDeclaration node;	
	
	public EnumDeclarationAdapter(EnumDeclaration node){
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {
	
		visitor.beforeVisit(node);
		boolean visitChildren = visitor.visit(node);
	
	    if (visitChildren) {
	    	acceptChildren(visitor, node.getEnumConstants());
	    	acceptChildren(visitor, node.getBodyDeclarations());
	    }
	    visitor.afterVisit(node);
	}
	

}
