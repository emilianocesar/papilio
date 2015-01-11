/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 * Representa una declaracin de atributo en la sintaxis abstracta
 * Puede ser un atributo de una clase (FieldDeclaration) o
 * una variable local (VariableDeclarationStatement) 
 */

public class ASAttributeDeclaration extends ASDeclaration {

	/**
	 * Genera un OFGNode para la declaracion de atributo con id
	 * igual al nombre completo del atributo
	 */
	@Override
	public Map<String, OFGNode> getOFGNodes() {
		
		if(this.isPrimitive()) {
			return Collections.emptyMap();
		}
		
		Map<String, OFGNode> result = new HashMap<String, OFGNode>();
		OFGNode attributeDeclarationNode = new OFGNode(this);
		attributeDeclarationNode.setId(this.getFullyScopedName());
		result.put(attributeDeclarationNode.getId(),attributeDeclarationNode);
		
		return result;
	}

}
