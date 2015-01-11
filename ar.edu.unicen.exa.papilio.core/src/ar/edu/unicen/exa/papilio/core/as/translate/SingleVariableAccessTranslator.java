package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.VariableDeclaration;

import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;


public class SingleVariableAccessTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		
		SingleVariableAccess variableReference = (SingleVariableAccess) node;
		List<ASElement> resultElements = new ArrayList<ASElement>();
		
		ASVariableAccess variableAccess = new ASVariableAccess();
		variableAccess.setNode(variableReference);
		VariableDeclaration variableDeclaration = variableReference.getVariable();
		
		ASAttributeDeclaration attributeDeclaration = (ASAttributeDeclaration) ASProgram.INSTANCE.getDeclaration(variableDeclaration);
		
		if (attributeDeclaration == null) {
			attributeDeclaration = (ASAttributeDeclaration) ASProgram.INSTANCE.addUndeclaredVariable(variableDeclaration);
		}
		variableAccess.setVariableDeclaration(attributeDeclaration);
		resultElements.add(variableAccess);
		return resultElements;
	}
	
}
