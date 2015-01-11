package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.Package;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class PackageAdapter extends ASTNodeAdapter {

	private Package node;
	
	public PackageAdapter(Package node) {
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    boolean visitChildren = visitor.visit(node);

	    if (visitChildren) {
	    	EList<Package> ownedPackages = node.getOwnedPackages();
	    	EList<AbstractTypeDeclaration> ownedElements = node.getOwnedElements();
	    	acceptChildren(visitor, ownedPackages);
	    	acceptChildren(visitor, ownedElements);
	    }
	    visitor.afterVisit(node);
	}
	

}
