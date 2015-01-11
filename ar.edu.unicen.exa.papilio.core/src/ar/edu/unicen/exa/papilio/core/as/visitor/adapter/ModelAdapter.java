package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class ModelAdapter extends ASTNodeAdapter {

	private Model node;
	
	public ModelAdapter(Model node) {
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    boolean visitChildren = visitor.visit(node);

	    if (visitChildren) {
	    	EList<Package> ownedElements = node.getOwnedElements();
	    	acceptChildren(visitor, ownedElements);
	    }
	    visitor.afterVisit(node);
	}
	
}
