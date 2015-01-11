/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Map;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public abstract class ASElement {
	
	protected ASTNode node;
	protected boolean isThisTarget = false;
	protected boolean isEmpty = false;
	protected Type typeCoercion = null;
	
	public abstract boolean isPrimitive();

	public abstract Map<String, OFGNode> getOFGNodes();
	
	public abstract Map<String, Object> getOFGEdges();
		
	public ASTNode getNode() {
		return node;
	}

	public void setNode(ASTNode node) {
		this.node = node;
	}
	
	public boolean isThisTarget() {
		return isThisTarget;
	}

	public void setThisTarget(boolean isThisTarget) {
		this.isThisTarget = isThisTarget;
	}
	
	/**
	 * Obtiene el id de arco para el arco conformado por
	 * los nodos con sourceId y targetId pasados como parametro
	 * El id de arco esta dado por el String sourceNodeId,targetNodeId 
	 * @param spurceNodeId El id del nodo source del arco 
	 * @param targetNodeId El id del nodo target del arco
	 * @return El id para el arco
	 */
	protected String getEdgeId(String sourceNodeId, String targetNodeId) {
		
		StringBuilder edgeId = new StringBuilder(sourceNodeId);
		edgeId.append(",");
		edgeId.append(targetNodeId);
		return edgeId.toString();
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public Type getTypeCoercion() {
		return typeCoercion;
	}

	public void setTypeCoercion(Type typeCoercion) {
		this.typeCoercion = typeCoercion;
	}

	
}
