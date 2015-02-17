package ar.edu.unicen.exa.papilio.core.as;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.Model;

import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;
import ar.edu.unicen.exa.papilio.core.as.visitor.adapter.ASTNodeAdapterFactory;
import ar.edu.unicen.exa.papilio.core.as.visitor.adapter.Visitable;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public class ASGenerator {


	public ASProgram iterate(EObject eObject) {
		Context context = new Context(new ASProgram());
		JavaModelVisitor visitor = new JavaModelVisitor(context);
		Visitable visitableNode = ASTNodeAdapterFactory.INSTANCE
				.adapt((Model) eObject);
		visitableNode.accept(visitor);
		return context.getProgram();
	}

}
