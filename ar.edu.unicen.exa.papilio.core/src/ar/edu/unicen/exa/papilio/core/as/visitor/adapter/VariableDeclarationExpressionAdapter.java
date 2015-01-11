package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.VariableDeclarationExpression;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class VariableDeclarationExpressionAdapter extends ASTNodeAdapter {

	private VariableDeclarationExpression node;
	
	public VariableDeclarationExpressionAdapter(VariableDeclarationExpression node) {
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
