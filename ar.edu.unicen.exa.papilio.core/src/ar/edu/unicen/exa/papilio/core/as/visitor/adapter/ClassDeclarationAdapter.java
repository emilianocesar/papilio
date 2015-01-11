package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmt.modisco.java.BodyDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class ClassDeclarationAdapter extends ASTNodeAdapter {

	private ClassDeclaration node;	
	
	public ClassDeclarationAdapter(ClassDeclaration node){
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
