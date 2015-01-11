/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.ReturnStatement;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

/**
 * @author Belen Rolandi
 *
 */
public class ReturnStatementAdapter extends ASTNodeAdapter {

	private ReturnStatement node;
	
	public ReturnStatementAdapter(ReturnStatement node) {
		
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
		visitor.visit(node);
		visitor.afterVisit(node);
	}

}
