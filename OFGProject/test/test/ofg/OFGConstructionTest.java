package test.ofg;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;

/**
 * @author Belen Rolandi
 * Testea la construccion del ofg para distintos modelos de ejemplo
 * Los tests verifican que el ofg generado tenga los nodos y los arcos
 * que corresponden para el programa testeado
 */
public class OFGConstructionTest {

	ASGenerator asGenerator = new ASGenerator();
	OFGGenerator ofgGenerator = new OFGGenerator();
	
	@Before
	public void setUp(){
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();
		OFGGraph.getInstance().removeAllNodes();
	}

	private Model loadModel(String path) {
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(path).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		return discoverer.loadJavaModel(uri.toString());
	}
	
	@Test
	public void ofgBinaryTreeConstructionTest(){
		String path = Constants.BINARY_TREE_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		ofgGenerator.generateOFG();
		OFGGraph resultOfg = OFGGraph.getInstance();
		
		Assert.assertTrue(resultOfg.getNodes().size() > 0);
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTree.BinaryTree.this"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.UniversityAdmin.students"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTree.insert.this"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.BinaryTreeNode.this"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.UniversityAdmin.addStudent.n"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTree.insert.node"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTree.root"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.insertNode.this"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.insertNode.node"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.left"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.right"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.Student.Student.this"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.UniversityAdmin.main.s1"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.UniversityAdmin.addStudent.s"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.BinaryTreeNode.x"));
		Assert.assertTrue(resultOfg.containsNode("binarytree.BinaryTreeNode.obj"));
		
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTree.BinaryTree.this", "binarytree.UniversityAdmin.students"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.UniversityAdmin.students", "binarytree.BinaryTree.insert.this"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.BinaryTreeNode.this", "binarytree.UniversityAdmin.addStudent.n"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.UniversityAdmin.addStudent.n", "binarytree.BinaryTree.insert.node"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTree.insert.node","binarytree.BinaryTree.root"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTree.insert.node","binarytree.BinaryTreeNode.insertNode.node"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTree.root","binarytree.BinaryTreeNode.insertNode.this"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.insertNode.node","binarytree.BinaryTreeNode.left"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.insertNode.node","binarytree.BinaryTreeNode.right"));
		
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.insertNode.node","binarytree.BinaryTreeNode.insertNode.node"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.left","binarytree.BinaryTreeNode.insertNode.this"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.right","binarytree.BinaryTreeNode.insertNode.this"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.Student.Student.this","binarytree.UniversityAdmin.main.s1"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.UniversityAdmin.main.s1","binarytree.UniversityAdmin.addStudent.s"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.UniversityAdmin.addStudent.s","binarytree.BinaryTreeNode.BinaryTreeNode.x"));
		Assert.assertTrue(resultOfg.containsEdge("binarytree.BinaryTreeNode.BinaryTreeNode.x","binarytree.BinaryTreeNode.obj"));
		
	}
	
	@Test
	public void ofgElibminConstructionTest() {
		String path = Constants.ELIBMIN_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		
		asGenerator.iterate(javaModel);
		ofgGenerator.generateOFG();
		OFGGraph resultOfg = OFGGraph.getInstance();
		
		//Verifico que esten todos los nodos
		Assert.assertEquals(43, resultOfg.getNodes().size());
		
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.title"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.loan"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.Document.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.Document.tit"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.isAvailable.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.authorizedLoan.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.authorizedLoan.user"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.addLoan.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Document.addLoan.ln"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.user"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.document"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.Loan.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.Loan.usr"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.Loan.doc"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.getUser.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.getUser.return"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.getDocument.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Loan.getDocument.return"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.fullName"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.loans"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.User.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.User.name"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.numberOfLoans.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.addLoan.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.User.addLoan.loan"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.MAX_NUMBER_OF_LOANS"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.loans"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.Library.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.borrowDocument.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.borrowDocument.user"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.borrowDocument.doc"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.borrowDocument.loan"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.addLoan.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.addLoan.loan"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.addLoan.user"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.addLoan.doc"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.main.this"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.main.userName"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.main.docName"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.main.me"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.main.doc"));
		Assert.assertTrue(resultOfg.containsNode("samples.elib.Library.main.myLibrary"));
						
		//Verifico los arcos
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Document.Document.tit", "samples.elib.Document.title"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Document.addLoan.ln", "samples.elib.Document.loan"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.Loan.usr", "samples.elib.Loan.user"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.Loan.doc", "samples.elib.Loan.document"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.User.User.name", "samples.elib.User.fullName"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.User.addLoan.loan","samples.elib.User.loans"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.user", "samples.elib.User.numberOfLoans.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.doc", "samples.elib.Document.isAvailable.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.doc", "samples.elib.Document.authorizedLoan.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.user", "samples.elib.Document.authorizedLoan.user"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.Loan.this", "samples.elib.Library.borrowDocument.loan"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.user", "samples.elib.Loan.Loan.usr"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.doc", "samples.elib.Loan.Loan.doc"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.this", "samples.elib.Library.addLoan.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.borrowDocument.loan", "samples.elib.Library.addLoan.loan"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.getUser.return", "samples.elib.Library.addLoan.user"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.loan", "samples.elib.Loan.getUser.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.getDocument.return", "samples.elib.Library.addLoan.doc"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.loan", "samples.elib.Loan.getDocument.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.loan", "samples.elib.Library.loans"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.user", "samples.elib.User.addLoan.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.loan", "samples.elib.User.addLoan.loan"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.doc", "samples.elib.Document.addLoan.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.addLoan.loan", "samples.elib.Document.addLoan.ln"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.User.User.this", "samples.elib.Library.main.me"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.main.userName", "samples.elib.User.User.name"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Document.Document.this", "samples.elib.Library.main.doc"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.main.docName", "samples.elib.Document.Document.tit"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.Library.this", "samples.elib.Library.main.myLibrary"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.main.myLibrary", "samples.elib.Library.borrowDocument.this"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.main.me", "samples.elib.Library.borrowDocument.user"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Library.main.doc", "samples.elib.Library.borrowDocument.doc"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.user", "samples.elib.Loan.getUser.return"));
		Assert.assertTrue(resultOfg.containsEdge("samples.elib.Loan.document", "samples.elib.Loan.getDocument.return"));
		
	}
	
	@Test
	public void ofgHamcrestConstructionTest() {
	
		String path = Constants.HAMCRTEST_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		
		asGenerator.iterate(javaModel);
		this.printAsProgram();
	}
	
	
	private void printAsProgram() {
		
		for (ASDeclaration declaration : ASProgram.INSTANCE.getDeclarations().values()) {
			System.out.println(declaration);
		}
		
		for (ASStatement statement : ASProgram.INSTANCE.getStatements()) {
			System.out.println(statement);
		}
	}
}
