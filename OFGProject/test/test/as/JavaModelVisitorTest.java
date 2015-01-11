package test.as;

import org.eclipse.gmt.modisco.java.Model;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.exception.ModelVisitorException;
import ar.edu.unicen.exa.papilio.core.as.visitor.JavaModelVisitor;
import ar.edu.unicen.exa.papilio.core.as.visitor.adapter.ASTNodeAdapterFactory;
import ar.edu.unicen.exa.papilio.core.as.visitor.adapter.Visitable;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class JavaModelVisitorTest {

	
	private Model javaModel;

	@Before
	public void setUp() throws Exception {
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(Constants.ELIBMIN_URI);
	}
	
	@Test
	public void modelVisitorTest() throws ModelVisitorException {

		Visitable visitableNode = ASTNodeAdapterFactory.INSTANCE.adapt(javaModel);
		JavaModelVisitor visitor = new JavaModelVisitor();
		visitableNode.accept(visitor);
	}

}