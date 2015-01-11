package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractVariablesContainer;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;

import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;

public class AbstractVariablesContainerTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		
		AbstractVariablesContainer varDeclaration = (AbstractVariablesContainer) node;
		List<ASElement> result = new ArrayList<ASElement>();

		for (VariableDeclarationFragment fragment : varDeclaration.getFragments()) {
			List<ASElement> fragmentResult = this.translateFragment(fragment);
			result.addAll(fragmentResult);
		}
		
		return result;
	}
	
	/**
	 * Traduce a sintaxis abstracta un fragmento de una declaracion de variable
	 * Por cada fragmento se genera un ASAttributeDeclaration conteniendo su declaracion
	 * Si el fragmento posee un initializer traducible a sintaxis abstracta
	 * se genera ademas un ASAssignmentStatement conteniendo la sintaxis abstracta
	 * para la sentencia fragmento = initializer
	 * @param fragment El VariableDeclarationFragment que se desea traducir
	 * @return Una lista de ASElement con el resultado de traducir el fragmento a
	 * sintaxis abstracta
	 */
	private List<ASElement> translateFragment(VariableDeclarationFragment fragment) {
		
		List<ASElement> resultElements = new ArrayList<ASElement>();

		//traduzco la declaracion del fragmento
		AbstractSyntaxTranslator translator = this
				.getTranslatorForNode(fragment);
		List<ASElement> elements = translator.translate(fragment);
		ASDeclaration fragmentDeclaration = (ASDeclaration)elements.get(0);
		
		//seteo el valor de isPrimitive
		if (fragment.getVariablesContainer().getType() != null) {
			Type declarationType = fragment.getVariablesContainer().getType().getType();
			boolean isPrimitive = this.isPrimitiveType(declarationType);
			fragmentDeclaration.setPrimitive(isPrimitive);
		}
		
		resultElements.add(fragmentDeclaration);

		//si el fragmento posee initializer, traduzco el initializer
		ASTNode initializer = fragment.getInitializer();
		if (initializer != null) {
			AbstractSyntaxTranslator initializerTranslator = this
					.getTranslatorForNode(initializer);
			if (initializerTranslator != null) {
				List<ASElement> initializerElements = initializerTranslator
						.translate(initializer);
				
				if (!(initializerElements.isEmpty())) {
					//agrego el for por el caso ConditionalExpression que genera dos elementos
					for (ASElement element : initializerElements) {
						ASAssignmentStatement assignmentElement = new ASAssignmentStatement();
						ASVariableAccess variableAccess = new ASVariableAccess();
						variableAccess
								.setVariableDeclaration((ASAttributeDeclaration) fragmentDeclaration);
						assignmentElement.setNode(fragment);
						assignmentElement.setLeftSide(variableAccess);
						assignmentElement.setRightSide(element);
						resultElements.add(assignmentElement);
					}
						
				}
			}
		}
		
		return resultElements;
	}

}
