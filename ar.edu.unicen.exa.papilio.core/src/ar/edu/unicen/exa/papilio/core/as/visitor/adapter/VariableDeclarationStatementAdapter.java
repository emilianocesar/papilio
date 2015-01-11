package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class VariableDeclarationStatementAdapter extends ASTNodeAdapter {

	private VariableDeclarationStatement node;
	
	public VariableDeclarationStatementAdapter(VariableDeclarationStatement node) {
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    boolean visitChildren = visitor.visit(node);
	
	    if (visitChildren) {
	    	EList<VariableDeclarationFragment> fragments = node.getFragments();
	    	acceptChildren(visitor, fragments);
	    }
	    visitor.afterVisit(node);
	}

}
