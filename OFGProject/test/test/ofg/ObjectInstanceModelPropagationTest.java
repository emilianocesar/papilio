package test.ofg;

import java.util.List;

import org.junit.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.AbstractMethodInvocation;
import org.eclipse.gmt.modisco.java.Block;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.ExpressionStatement;
import org.eclipse.gmt.modisco.java.IfStatement;
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.Statement;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGenerator;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.ForwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.PropagationDirectionStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.FlowPropagationSetInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.ObjectIdentifierInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.ObjectIdentifierPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.PropagationStrategy;

/**
 * @author Belen Rolandi
 * Test de unidad para la clase ObjectIdentifierPropagationStrategy
 * que se encarga de almacenar en el modelo la informacion de los objetos
 * instanciados en el programa y las invocaciones de metodo realizadas
 * sobre estos objetos
 */
public class ObjectInstanceModelPropagationTest {

	Model javaModel;
	
	@Before
	public void setUp(){
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();
		OFGGraph.getInstance().removeAllNodes();
	}
	
	private void createOFG(String modelPath) {
		
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
	/**
	 * Comprueba que los objetos creados para cada clase sean almacenados
	 * correctamente en la lista instances contenida dentro de ClassDeclaration
	 */
	public void objectlnstancesModelStorageTest(){
	
		createOFG(Constants.ELIBMIN_MODEL_PATH);
		
		FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
		PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
		
		FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		
		ofgObjectAlgorithm.propagate(OFGGraph.getInstance());
		
		//testeo resultados
		ClassDeclaration library = (ClassDeclaration)((CompilationUnit)javaModel.getCompilationUnits().get(1)).getTypes().get(0);
		EList<InstanceSpecification> libraryInstances = library.getInstances();
		Assert.assertEquals(1, libraryInstances.size());
		String expectedLibrayInstanceName = "Library1";
		String actualLibrayInstanceName = ((InstanceSpecification)libraryInstances.get(0)).getName();
		Assert.assertEquals(expectedLibrayInstanceName, actualLibrayInstanceName);
		
		ClassDeclaration document = (ClassDeclaration)((CompilationUnit)javaModel.getCompilationUnits().get(0)).getTypes().get(0);
		EList<InstanceSpecification> documentInstances = document.getInstances();
		Assert.assertEquals(1, documentInstances.size());
		String expectedDocumentInstanceName = "Document1";
		String actualDocumentInstanceName = ((InstanceSpecification)documentInstances.get(0)).getName();
		Assert.assertEquals(expectedDocumentInstanceName, actualDocumentInstanceName);
		
		ClassDeclaration loan = (ClassDeclaration)((CompilationUnit)javaModel.getCompilationUnits().get(2)).getTypes().get(0);
		EList<InstanceSpecification> loanInstances = loan.getInstances();
		Assert.assertEquals(1, loanInstances.size());
		String expectedLoanInstanceName = "Loan1";
		String actualLoanInstanceName = ((InstanceSpecification)loanInstances.get(0)).getName();
		Assert.assertEquals(expectedLoanInstanceName, actualLoanInstanceName);
		
		ClassDeclaration user = (ClassDeclaration)((CompilationUnit)javaModel.getCompilationUnits().get(3)).getTypes().get(0);
		EList<InstanceSpecification> userInstances = user.getInstances();
		Assert.assertEquals(1, userInstances.size());
		String expectedUserInstanceName = "User1";
		String actualUserInstanceName = ((InstanceSpecification)userInstances.get(0)).getName();
		Assert.assertEquals(expectedUserInstanceName, actualUserInstanceName);
	}
	
	@Test
	/**
	 * Test que comprueba si las invocaciones de metodo efectuadas sobre una instancia
	 * fueron correctamente agregadas a la lista usages dentro del elemento InstanceSpecification
	 * correspondiente a la instancia
	 * Testea el caso de las invocaciones que poseen un target object, es decir, que son del tipo
	 * y.m   
	 */
	public void objectTargetInvocationInstanceUsagesTest(){
					
		createOFG(Constants.ELIBMIN_MODEL_PATH);
		
		FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
		PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
		
		FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		
		ofgObjectAlgorithm.propagate(OFGGraph.getInstance());
		
		//Testeo resultados
		
		ClassDeclaration library = (ClassDeclaration)((CompilationUnit)javaModel.getCompilationUnits().get(1)).getTypes().get(0);
		MethodDeclaration addLoan = (MethodDeclaration)library.getBodyDeclarations().get(4);
		//obtengo el elemento MethodInvocation asociado a la invocacion loan.getUser()
		MethodInvocation expectedLoanGetUserMethodInvocation = (MethodInvocation)((VariableDeclarationStatement)addLoan.getBody().getStatements().get(1)).getFragments().get(0).getInitializer();
		//obtengo el elemento MethodInvocation asociado a la invocacion loan.getDocument()
		MethodInvocation expectedLoanGetDocumentMethodInvocation = (MethodInvocation)((VariableDeclarationStatement)addLoan.getBody().getStatements().get(2)).getFragments().get(0).getInitializer();
		
		//obtengo la instancia Loan1 de la lista de instancias de loan
		ClassDeclaration loan = (ClassDeclaration)((CompilationUnit)javaModel.getCompilationUnits().get(2)).getTypes().get(0);
		//Obtengo la instancia creada de la clase Loan
		InstanceSpecification loan1Instance = loan.getInstances().get(0); 

		//Comparo los resultados del test
		MethodInvocation actualLoanGetUserMethodInvocation = (MethodInvocation)loan1Instance.getUsages().get(1);
		MethodInvocation actualLoanGetDocumentMethodInvocation = (MethodInvocation)loan1Instance.getUsages().get(2);
		Assert.assertEquals(expectedLoanGetUserMethodInvocation, actualLoanGetUserMethodInvocation);
		Assert.assertEquals(expectedLoanGetDocumentMethodInvocation, actualLoanGetDocumentMethodInvocation);
	}
	
	
	@Test
	/**
	 * Test que chequea que las invocaciones a metodos efectuados por una instancia hayan
	 * sido correctamente agregadas a la lista usages de la instancia
	 * Testea el caso en que el metodo invocado posee target this, es decir,
	 * invocaciones del tipo this.m
	 * El test se realiza sobre el modelo generado para el proyecto eLibmin
	 */
	public void thisTargetInvocationInstanceUsagesTest(){
		
		createOFG(Constants.ELIBMIN_MODEL_PATH);
		
		FlowPropagationSetInitializationStrategy<InstanceSpecification> genStrategy = new ObjectIdentifierInitializationStrategy();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> inStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> outStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		FlowPropagationSetInitializationStrategy<InstanceSpecification> killStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		PropagationStrategy<InstanceSpecification> propagationStrategy = new ObjectIdentifierPropagationStrategy();
		PropagationDirectionStrategy directionStrategy = new ForwardPropagationStrategy();
		
		FlowPropagationAlgorithm<InstanceSpecification> ofgObjectAlgorithm = new FlowPropagationAlgorithm<InstanceSpecification>(directionStrategy, inStrategy, outStrategy, genStrategy, killStrategy, propagationStrategy);
		
		ofgObjectAlgorithm.propagate(OFGGraph.getInstance());
		
		//obtengo la declaracion de la clase library
		Package elibPackage = ((Package)javaModel.getOwnedElements().get(0)).getOwnedPackages().get(0);
		ClassDeclaration library = (ClassDeclaration)elibPackage.getOwnedElements().get(1);
		//obtengo la declaracion del metodo borrwoDocument 
		MethodDeclaration libraryBorrowDocument = (MethodDeclaration)(library.getBodyDeclarations().get(3));
		//obtengo la invocacion a addLoan dentro del metodo borrowDocument
		Statement borrowDocumentAddLoanStatement = ((Block)((IfStatement)libraryBorrowDocument.getBody().getStatements().get(1)).getThenStatement()).getStatements().get(2);
		MethodInvocation expectedBorrowDocumentAddLoanInvocation = (MethodInvocation)((ExpressionStatement) borrowDocumentAddLoanStatement).getExpression();
		
		//obtengo la instancia creada de la clase library
		InstanceSpecification libraryInstance = library.getInstances().get(0);
		// obtengo la invocacion dentro de la instancia que hace referencia a addLoan
		List<AbstractMethodInvocation> usages = libraryInstance.getUsages();
		//tengo que preguntar de esta forma porque no siempre coloca las invocaciones en el mismo orden
		AbstractMethodInvocation actualBorrowDocumentAddLoanInvocation = (usages.get(1).getMethod().getName().equals("addLoan")) ? usages.get(1) : usages.get(2);
		Assert.assertEquals(expectedBorrowDocumentAddLoanInvocation, actualBorrowDocumentAddLoanInvocation);
	}
	
}