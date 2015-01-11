package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.Block;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class ConstructorDeclarationAdapter extends ASTNodeAdapter {

private ConstructorDeclaration node;	
	
	public ConstructorDeclarationAdapter(ConstructorDeclaration node) {
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
	   visitor.beforeVisit(node);
	   boolean  visitChildren = visitor.visit(node);
	
	    if (visitChildren) {
	    	Block body = node.getBody();
	    	acceptChild(visitor, body);
	    }
	    visitor.afterVisit(node);
	}

}
