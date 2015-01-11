package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.VariableDeclaration;

import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class VariableDeclarationTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		VariableDeclaration declaration = (VariableDeclaration) node;
		List<ASElement> resultElements = new ArrayList<ASElement>();

		String attributeFullName = this.context.getFullVarDeclarationName(declaration);

		ASAttributeDeclaration attributeDeclaration = new ASAttributeDeclaration();
		attributeDeclaration.setFullyScopedName(attributeFullName);
		attributeDeclaration.setNode(declaration);
		resultElements.add(attributeDeclaration);
		return resultElements;
	}

}
