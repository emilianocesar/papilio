package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class SingleVariableDeclarationTranslator extends
		VariableDeclarationTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		
		SingleVariableDeclaration param = (SingleVariableDeclaration)node;
		
		Type paramType = param.getType().getType();
		boolean isPrimitive = this.isPrimitiveType(paramType);
				
		List<ASElement> result = super.translate(node);
		
		//seteo valor de isPrimitive
		ASDeclaration asDeclaration = (ASDeclaration)result.get(0);
		asDeclaration.setPrimitive(isPrimitive);
		
		return result;
	}
}
