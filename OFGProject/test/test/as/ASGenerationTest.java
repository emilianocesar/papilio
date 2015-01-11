package test.as;

import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.Model;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ModelVisitorException;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

/**
 * @author Belen Rolandi
 *
 */
public class ASGenerationTest {
	
	private Model javaModel;

	
	@Test
	public void asGenerationTest() throws ModelVisitorException {
	
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		Model javaModel = discoverer.loadJavaModel(Constants.MODEL_URI);
						
		ASGenerator asGenerator = new ASGenerator();
		asGenerator.iterate(javaModel);
//		asGenerator.printAsProgram();		
	}
	
	@Test
	public void printClasses(){
		
		for(CompilationUnit unit : javaModel.getCompilationUnits()){
		
			ClassDeclaration clase = (ClassDeclaration)unit.getTypes().get(0);
			System.out.println(clase.getName());
		}
	}
	
	@Test
	public void eLibMinAsGenerationTest() throws ModelVisitorException {
	
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		Model javaModel = discoverer.loadJavaModel(Constants.ELIBMIN_URI);
		
		ASGenerator asGenerator = new ASGenerator();
		asGenerator.iterate(javaModel);
		printAsProgram(ASProgram.INSTANCE);
	}
	
	private void printAsProgram(ASProgram program) {
		
		for (ASElement declaration : program.getDeclarations().values()) {
//			System.out.println(declaration.getNode() + "=>" + declaration);
			System.out.println(declaration);
		}
		
//		for (ASDeclarationProxy undeclared: program.getUndeclaredElement().values()) {
//			System.out.println(((ASElement)undeclared).getNode() + "=>" + undeclared);
//		}
		
		for (ASElement statement : program.getStatements()) {
			System.out.println(statement);
		}
	} 
}
