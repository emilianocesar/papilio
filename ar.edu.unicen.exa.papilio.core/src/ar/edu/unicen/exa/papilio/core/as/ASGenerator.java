package ar.edu.unicen.exa.papilio.core.as;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmt.modisco.java.Model;

import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;
import ar.edu.unicen.exa.papilio.core.as.visitor.adapter.ASTNodeAdapterFactory;
import ar.edu.unicen.exa.papilio.core.as.visitor.adapter.Visitable;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public class ASGenerator {

	private JavaModelVisitor visitor = new JavaModelVisitor();

	public List<ASTranslatorException> iterate(EObject eObject) {

		Visitable visitableNode = ASTNodeAdapterFactory.INSTANCE
				.adapt((Model) eObject);
		visitableNode.accept(visitor);
//		List<ASTranslatorException> errors = new ArrayList<ASTranslatorException>(); 
//		for (ASTranslatorException e: ASProgram.INSTANCE.getErrors()) {
//			if (ASTranslatorExceptionLevel.ERROR.equals(e.getLevel())) {
//				errors.add(e);
//			}
//		}
		return ASProgram.INSTANCE.getErrors();
	}

}
