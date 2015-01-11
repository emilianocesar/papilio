package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class DefaultAdapter extends ASTNodeAdapter {

	ASTNode node;

	public DefaultAdapter(ASTNode node) {
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {
		
		boolean visitChildren = visitor.defaultVisit(node);
		if (visitChildren) {
			for (EObject childEObject : node.eContents()) {
				acceptChild(visitor, (ASTNode)childEObject);
			}
		}
	}
}
