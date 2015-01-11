package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.CompilationUnit;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public class CompilationUnitAdapter extends ASTNodeAdapter {

	private CompilationUnit node;

	public CompilationUnitAdapter(CompilationUnit node) {
		this.node = node;
	}

	@Override
	public void accept(JavaModelVisitor visitor) {
		visitor.beforeVisit(node);
		visitor.visit(node);
		visitor.afterVisit(node);

	}

}
