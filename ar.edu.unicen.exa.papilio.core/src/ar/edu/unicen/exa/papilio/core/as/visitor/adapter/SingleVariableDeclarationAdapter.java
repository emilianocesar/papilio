/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

/**
 * @author Rolandis
 *
 */
public class SingleVariableDeclarationAdapter extends ASTNodeAdapter {
	
	private SingleVariableDeclaration node;

	public SingleVariableDeclarationAdapter(SingleVariableDeclaration node){
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    visitor.visit(node);
	    visitor.afterVisit(node);

	}

}
