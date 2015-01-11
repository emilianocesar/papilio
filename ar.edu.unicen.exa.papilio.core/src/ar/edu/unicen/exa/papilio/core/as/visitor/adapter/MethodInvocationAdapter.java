package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.MethodInvocation;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class MethodInvocationAdapter extends ASTNodeAdapter {

private MethodInvocation node;	
	
	public MethodInvocationAdapter(MethodInvocation node){
		this.node = node;
	}
	
	@Override
	public void accept(JavaModelVisitor visitor) {
		
		visitor.beforeVisit(node);
	    boolean visitChildren = visitor.visit(node);
	
	    if (visitChildren) {
	    	EList<Expression> arguments = node.getArguments();
	    	acceptChildren(visitor, arguments);
	    }
	    visitor.afterVisit(node);
	}

}
