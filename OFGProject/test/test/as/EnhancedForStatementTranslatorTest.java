package test.as;

import org.junit.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.EnhancedForStatement;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class EnhancedForStatementTranslatorTest {

	private ASGenerator asGenerator = new ASGenerator();

	@Before
	public void setUp() {
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();	
	}

	
	private Model loadModel(String path) {
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(path).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		return discoverer.loadJavaModel(uri.toString());
	}
	
	@Test
	public void translateEnhancedForStatementSingleVariableAccessExpression() {
		
		//Collection<Element>elements = new ArrayList<Element>()
		//for(Element element : elements)
		
		String path = Constants.ENHANCED_FOR_SINGLE_VAR_ACCESS_EXPRESSION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Assert.assertEquals(1, ASProgram.INSTANCE.getStatements().size());
		
		//Obtengo el statement
		ASStatement asStatement = ASProgram.INSTANCE.getStatements().get(0);
		Assert.assertTrue(asStatement instanceof ASAssignmentStatement);
		ASAssignmentStatement asAssignment = (ASAssignmentStatement)asStatement;
		Assert.assertTrue(asAssignment.getLeftSide() instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asParameter = (ASAttributeDeclaration)asAssignment.getLeftSide();
		Assert.assertTrue(asAssignment.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess asCollection = (ASVariableAccess)asAssignment.getRightSide();
		String expectedLeftSideName = "test.TesisTest.testMethod.elem";
		String actualLeftSideName = asParameter.getFullyScopedName();
		String expectedRightSideName = "test.TesisTest.elements";
		String actualRightSideName = asCollection.getFullyScopedName();
		Assert.assertEquals(expectedLeftSideName, actualLeftSideName);
		Assert.assertEquals(expectedRightSideName, actualRightSideName);
		
		//verifico que se agrego el nodo para el parametro
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		MethodDeclaration aMethod = (MethodDeclaration)aClass.getBodyDeclarations().get(1);
		EnhancedForStatement enhancedFor = (EnhancedForStatement)aMethod.getBody().getStatements().get(0);
		SingleVariableDeclaration parameterNode = enhancedFor.getParameter();
		
		Assert.assertTrue(ASProgram.INSTANCE.getDeclarations().containsKey(parameterNode));
		
	}
	
	@Test
	public void translateEnhancedForStatementArrayAccessExpression() {
		
		//Element[] elements
		//for(Element element : elements)

		String path = Constants.ENHANCED_FOR_ARRAY_ACCESS_MODEL_EXPRESSION_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Assert.assertEquals(1, ASProgram.INSTANCE.getStatements().size());
		
		//es el mismo caso que el ejemplo anterior, no tiene mucho sentido
		//volver a testear los resultados
	}
	
	@Test
	public void translateEnhancedForStatementNonTranslatableExpression() {
		
		//for(Element element : this.getElements())
		
		String path = Constants.ENHANCED_FOR_NON_TRANSLATABLE_EXPRESSION_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//Compruebo que no se generan statements
		Assert.assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
		
	}


}
