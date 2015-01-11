package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public abstract class ASTNodeAdapter implements Visitable {

	public ASTNodeAdapter(){}
	
	@Override
	public abstract void accept(JavaModelVisitor visitor);

	protected void acceptChildren(JavaModelVisitor visitor, EList<? extends ASTNode> children) {
		if (children != null) {
			for (ASTNode child : children) {
				Visitable visitableNode = ASTNodeAdapterFactory.INSTANCE.adapt(child); 
				visitableNode.accept(visitor);
			}
		}
	}
	
	protected void acceptChild(JavaModelVisitor visitor, ASTNode child) {
		if (child != null) {
			Visitable visitableNode = ASTNodeAdapterFactory.INSTANCE.adapt(child);
			visitableNode.accept(visitor);
	    }
	}

}
