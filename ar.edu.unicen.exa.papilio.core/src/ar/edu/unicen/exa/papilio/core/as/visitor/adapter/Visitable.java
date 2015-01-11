package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;

public interface Visitable {

	public void accept(JavaModelVisitor v);
	
}
