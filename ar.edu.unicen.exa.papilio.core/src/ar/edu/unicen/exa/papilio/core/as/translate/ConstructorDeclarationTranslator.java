package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

/**
 * 
 * @author Belen Rolandi
 *
 */
public class ConstructorDeclarationTranslator extends AbstractTranslator {

	/**
	 * Traduce un elemento de tipo ConstructorDeclaration a su sintaxis
	 * abstracta Genera un elemento ASConstructorDeclaration con el nombre
	 * completo del constructor y una lista con elementos ASFormalParameter, uno
	 * por cada parametro formal declarado
	 * 
	 * @param node
	 *            El ConstructorDeclaration a traducir
	 * @return Una lista con el elemento ASConstructorDeclaration resultante
	 */
	public List<ASElement> translate(ASTNode node) {
		
		List<ASElement> resultElements = new ArrayList<ASElement>();

		ConstructorDeclaration constructorDeclarationNode = (ConstructorDeclaration)node;
		ASConstructorDeclaration asConstructorDeclaration = new ASConstructorDeclaration();
		asConstructorDeclaration.setFullyScopedName(context.getFullMethodDeclarationName(constructorDeclarationNode));
		
		//seteo valor del atributo isPrimitive en true si el constructor pertenece
		//a alguna clase wrapper
		Type constructorType = constructorDeclarationNode.getAbstractTypeDeclaration();
		boolean isPrimitive = this.isWrapperType(constructorType);
		asConstructorDeclaration.setPrimitive(isPrimitive);		
		
		//traduzco la lista de parametros formales
		for (SingleVariableDeclaration formalParameter : constructorDeclarationNode.getParameters()) {
			AbstractSyntaxTranslator formalParameterTranslator = this.getTranslatorForNode(formalParameter);
			ASAttributeDeclaration asFormalParameter = (ASAttributeDeclaration)formalParameterTranslator.translate(formalParameter).get(0);
			resultElements.add(asFormalParameter);
			asConstructorDeclaration.getFormalParameters().add(asFormalParameter);
		}
		
		asConstructorDeclaration.setNode(constructorDeclarationNode);
		resultElements.add(asConstructorDeclaration);
		return resultElements;
	}
	

}
