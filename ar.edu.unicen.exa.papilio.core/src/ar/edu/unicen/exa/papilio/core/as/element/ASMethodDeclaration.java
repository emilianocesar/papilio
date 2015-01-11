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
public class ASMethodDeclaration extends ASDeclaration {

	private List<ASAttributeDeclaration> formalParameters = new ArrayList<ASAttributeDeclaration>();
	private Boolean returnsObject;
	private List<ASNamedElement> returnObjects = new ArrayList<ASNamedElement>();

	public Boolean returnsObject() {
		return returnsObject;
	}

	public void setReturnsObject(Boolean returnsObject) {
		this.returnsObject = returnsObject;
	}

	public List<ASAttributeDeclaration> getFormalParameters() {
		return formalParameters;
	}

	public void setFormalParameters(List<ASAttributeDeclaration> formalParameters) {
		this.formalParameters = formalParameters;
	}
	
	
	public String toString() {
		
		StringBuilder methodDeclaration = new StringBuilder(fullyScopedName);
		methodDeclaration.append("(");
		if (formalParameters.size() > 0) {
			
			methodDeclaration.append(formalParameters.get(0).toString());
			
			for(int i=1; i<formalParameters.size(); i++) {
				
				methodDeclaration.append(",");
				methodDeclaration.append(formalParameters.get(i).toString());
			}
		}
		
		methodDeclaration.append(")");
		return methodDeclaration.toString();
	}
	
	
	/**
	 * Genera un nodo para la declaracion del metodo con identificador m.this
	 * donde m es el nombre completo del metodo
	 * Si el metodo retorna un objeto, se crea otro nodo con identificador m.return
	 * representando el objeto retornado
	 */
	@Override
	public Map<String, OFGNode> getOFGNodes() {
		
		if (this.isPrimitive()) {
			return Collections.emptyMap();
		}

		Map<String, OFGNode> result = new HashMap<String, OFGNode>();
		
		//creo un OFGNode para la declaracion del metodo con id = m.this
		StringBuilder methodDeclarationId = new StringBuilder(this.getFullyScopedName());
		methodDeclarationId.append(".this");
		
		OFGNode methodDeclarationNode = new OFGNode(this);
		methodDeclarationNode.setId(methodDeclarationId.toString());
		result.put(methodDeclarationNode.getId(),methodDeclarationNode);
		
		//si el metodo retorna un objeto creo un OFGNode con id m.return
		if(this.returnsObject) {
			
			StringBuilder methodReturnId = new StringBuilder(this.getFullyScopedName());
			methodReturnId.append(".return");
			
			OFGNode methodReturnNode = new OFGNode(this);
			methodReturnNode.setId(methodReturnId.toString());
			result.put(methodReturnNode.getId(), methodReturnNode);
		}
		
		return result;
	}
	
	@Override
	public Map<String, Object> getOFGEdges() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		//si el metodo posee objetos de retorno, agrego arcos (returnObject, m.return)
			if(!(returnObjects.isEmpty())) {
				for (ASNamedElement returnObject : returnObjects) {
					String returnObjectid = returnObject.getFullyScopedName();
					StringBuilder methodReturnId = new StringBuilder(this.getFullyScopedName());
					methodReturnId.append(".return");
					String edgeId = this.getEdgeId(returnObjectid, methodReturnId.toString());
					//Agrego como valor del arco el elemento returnObject
					ASElement value = (ASElement)returnObject;
					result.put(edgeId, value);
				}
			}
		return result;
	}

	public List<ASNamedElement> getReturnObjects() {
		return returnObjects;
	}

}
