package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;



import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.CatchClause;
import org.eclipse.gmt.modisco.java.Statement;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class CatchClauseAdapter extends ASTNodeAdapter {
	
	private CatchClause node;

	public CatchClauseAdapter(CatchClause node) {
		this.node = node; 
		
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    boolean visitChildren = visitor.visit(node);
	
	    if (visitChildren) {
	    	acceptChild(visitor, node.getException());
	    	EList<Statement> statements = node.getBody().getStatements();
	    	acceptChildren(visitor, statements);
	    }
	    visitor.afterVisit(node);
	}

}
