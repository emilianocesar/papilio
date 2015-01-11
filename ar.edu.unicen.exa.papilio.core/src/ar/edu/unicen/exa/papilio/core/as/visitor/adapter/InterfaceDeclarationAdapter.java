/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.InterfaceDeclaration;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

/**
 * @author Rolandis
 *
 */
public class InterfaceDeclarationAdapter extends ASTNodeAdapter {

	InterfaceDeclaration node;
	
	public InterfaceDeclarationAdapter(InterfaceDeclaration node) {
		
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {

		visitor.beforeVisit(node);
		boolean visitChildren = visitor.visit(node);
		
		if (visitChildren) {
	    	EList<BodyDeclaration> bodyDeclarations = node.getBodyDeclarations();
	    	acceptChildren(visitor, bodyDeclarations);
	    }
	    visitor.afterVisit(node);
	}

}
