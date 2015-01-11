package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.PrimitiveTypeVoid;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;

import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclaration;

public class MethodDeclarationTranslator extends AbstractTranslator {

	/**
	 * Traduce un elemento de tipo MethodDeclaration a su sintaxis abstracta Se
	 * crea un elemento ASMethodDeclaration que posee el nombre completo del
	 * metodo y una lista con un elemento ASParameter por cada parametro formal
	 * definido
	 * 
	 * @param node
	 *            El elemento MethodDeclaration a traducir
	 * @return Una lista conteniendo el elemento ASMethodDeclaration resultante
	 */
	@Override
	public List<ASElement> translate(ASTNode node) {
		
		List<ASElement> resultElements = new ArrayList<ASElement>();

		MethodDeclaration methodDeclarationNode = (MethodDeclaration)node;
		ASMethodDeclaration asMethodDeclaration = new ASMethodDeclaration();
		asMethodDeclaration.setFullyScopedName(context.getFullMethodDeclarationName(methodDeclarationNode));
		
		//seteo valor del atributo isPrimitive
		if (methodDeclarationNode.getReturnType() != null) {
			Type methodType = methodDeclarationNode.getReturnType().getType();
			boolean isPrimitive = this.isPrimitiveType(methodType);
			asMethodDeclaration.setPrimitive(isPrimitive);
		}

		//traduzco la lista de parametros formales
		for (SingleVariableDeclaration formalParameter : methodDeclarationNode.getParameters()) {
			AbstractSyntaxTranslator formalParameterTranslator = this.getTranslatorForNode(formalParameter);
			ASAttributeDeclaration asFormalParameter = (ASAttributeDeclaration)formalParameterTranslator.translate(formalParameter).get(0);
			resultElements.add(asFormalParameter);
			asMethodDeclaration.getFormalParameters().add(asFormalParameter);
		}
		
		Boolean returnsObject = this.hasObjectReturnType(methodDeclarationNode);
		asMethodDeclaration.setReturnsObject(returnsObject);
		asMethodDeclaration.setNode(methodDeclarationNode);
		resultElements.add(asMethodDeclaration);
		return resultElements;
	}

	
	/**
	 * Verifica si la declaracion de metodo pasada como parametro retorna como resultado
	 * una referencia a un objeto
	 * Un metodo retorna un objeto si posee tipo de retorno y ese tipo no es un tipo primitivo 
	 * @param methodDeclaration La declaracion de metodo que se desea verificar
	 * @return True si el metodo retorna un objeto, false en caso contrario
	 */
	private Boolean hasObjectReturnType(MethodDeclaration methodDeclaration) {
		
		TypeAccess returnType = methodDeclaration.getReturnType();
		if (returnType == null) {
			return false;
		}
		Type type = returnType.getType();
		if (type instanceof PrimitiveTypeVoid) {
			return false;
		} else {
			return !(isPrimitiveType(type));
		  }
	}

}
