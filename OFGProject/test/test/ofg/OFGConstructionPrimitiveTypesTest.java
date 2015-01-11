package test.ofg;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;

/**
 * @author Belen Rolandi
 * Testea que los elementos de tipos primitivos sean ignorados
 * al generar el ofg
 */
public class OFGConstructionPrimitiveTypesTest {
	
	private static Model javaModel;
	
	@BeforeClass
	public static void setUp(){
		
//		ASProgram.INSTANCE.removeAllDeclarations();
//		ASProgram.INSTANCE.removeAllStatements();
//		ASProgram.INSTANCE.removeAllUndeclared();
//		OFGGraph.getInstance().removeAllNodes();
		createOFG(Constants.ELIB_MODEL_PATH);
	}
	
	private static void createOFG(String modelPath) {
		
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(modelPath).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		javaModel = discoverer.loadJavaModel(uri.toString());
		
		ASGenerator asGenerator = new ASGenerator();
		OFGGenerator ofgGenerator = new OFGGenerator();	
		asGenerator.iterate(javaModel);
		ofgGenerator.generateOFG();
	}
	
		
	@Test
	public void methodDeclarationOFGGenerationTest() {
		
		OFGGraph ofg = OFGGraph.getInstance();
		
		Assert.assertFalse(ofg.containsNode("samples.elib.Library.searchDocumentByISBN.this"));
		Assert.assertFalse(ofg.containsNode("samples.elib.Library.isHolding.this"));
		Assert.assertTrue(ofg.containsNode("samples.elib.Library.addLoan.this"));
		Assert.assertTrue(ofg.containsNode("samples.elib.Library.printAllLoans.this"));
	}
	
	@Test
	public void methodInvocationOFGGenerationTest() {
		
		OFGGraph ofg = OFGGraph.getInstance();
		
		Assert.assertTrue(ofg.containsEdge("samples.elib.Library.this", "samples.elib.Library.addLoan.this"));
		Assert.assertFalse(ofg.containsEdge("samples.elib.Library.borrowDocument.user", "samples.elib.User.numberOfLoans.this"));
		Assert.assertTrue(ofg.containsNode("samples.elib.Library.borrowDocument.doc"));
		Assert.assertFalse(ofg.containsEdge("samples.elib.Library.borrowDocument.doc", "samples.elib.Document.isAvailable.this"));
		Assert.assertFalse(ofg.containsEdge("samples.elib.Library.borrowDocument.doc", "samples.elib.Document.authorizedLoan.this"));
	}

	@Test
	public void variableDeclarationOFGGenrationTest() {
		
		OFGGraph ofg = OFGGraph.getInstance();
		
		Assert.assertFalse(ofg.containsNode("samples.elib.Document.documentCode"));
		Assert.assertFalse(ofg.containsNode("samples.elib.Document.authors"));
		Assert.assertFalse(ofg.containsNode("samples.elib.User.address"));
	}
	
	@Test
	public void assignmentOFGGenerationTest() {
		
		OFGGraph ofg = OFGGraph.getInstance();
		
		Assert.assertTrue(ofg.containsEdge("samples.elib.Loan.getUser.return","samples.elib.Library.removeLoan.user"));
		Assert.assertFalse(ofg.containsEdge("samples.elib.Document.Document.tit","samples.elib.Document.title"));
		 
	}
}
