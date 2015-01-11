package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.EnhancedForStatement;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class EnhancedForStatementAdapter extends ASTNodeAdapter {

	private EnhancedForStatement node;	
	
	public EnhancedForStatementAdapter(EnhancedForStatement node) {
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    boolean visitChildren = visitor.visit(node);
	
	    if (visitChildren) {
	    	acceptChild(visitor, node.getBody());
	    }
	    visitor.afterVisit(node);
	}

}
