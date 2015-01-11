/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public class ASConstructorDeclaration extends ASDeclaration {

	private List<ASAttributeDeclaration> formalParameters = new ArrayList<ASAttributeDeclaration>();

	public List<ASAttributeDeclaration> getFormalParameters() {
		return formalParameters;
	}

	public void setFormalParameters(List<ASAttributeDeclaration> formalParameters) {
		this.formalParameters = formalParameters;
	}
	
	public String toString(){
		
		StringBuilder constructorDeclaration = new StringBuilder(fullyScopedName);
		constructorDeclaration.append("(");
		
		if (formalParameters.size() > 0) {
			
			constructorDeclaration.append(formalParameters.get(0).toString());
			
			for(int i=1; i<formalParameters.size(); i++) {
				
				constructorDeclaration.append(",");
				constructorDeclaration.append(formalParameters.get(i).toString());
			}
		}
		
		constructorDeclaration.append(")");
		return constructorDeclaration.toString();
	}
	
	/**
	 * Genera un nodo para el constructor con identificador cs.this
	 * donde cs corresponde al nombre completo del constructor
	 */
	@Override
	public Map<String, OFGNode> getOFGNodes() {
		
		if (this.isPrimitive()) {
			return Collections.emptyMap();
		}
		
		Map<String, OFGNode> result = new HashMap<String, OFGNode>();
		
		StringBuilder constructorNodeId = new StringBuilder(this.getFullyScopedName());
		constructorNodeId.append(".this");
		
		OFGNode constructorDeclarationNode = new OFGNode(this);
		constructorDeclarationNode.setId(constructorNodeId.toString());
		result.put(constructorNodeId.toString(),constructorDeclarationNode);
						
		return result;
	}
	
}
