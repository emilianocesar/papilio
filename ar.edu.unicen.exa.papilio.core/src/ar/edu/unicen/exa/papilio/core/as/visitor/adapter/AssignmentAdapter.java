package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.Assignment;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class AssignmentAdapter extends ASTNodeAdapter {

	Assignment node;
	
	public AssignmentAdapter(Assignment node){
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
		visitor.visit((Assignment)node);
        visitor.afterVisit(node);
	}
}
