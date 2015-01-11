/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public class ASAssignmentStatement extends ASStatement {
	
	private ASElement leftSide;
	private ASElement rightSide;

	public ASElement getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(ASElement leftSide) {
		this.leftSide = leftSide;
	}

	
	public ASElement getRightSide() {
		return rightSide;
	}


	public void setRightSide(ASElement rightSide) {
		this.rightSide = rightSide;
	}
	
	public String toString() {
		
		StringBuilder stringAssignment = new StringBuilder(leftSide.toString());
		stringAssignment.append(" = ");
		stringAssignment.append(rightSide.toString());
		return stringAssignment.toString();
	}
	
	@Override
	public Map<String, OFGNode> getOFGNodes() {
		Map<String, OFGNode> resultNodes = new HashMap<String, OFGNode>();
		resultNodes.putAll(leftSide.getOFGNodes());
		resultNodes.putAll(rightSide.getOFGNodes());
		return resultNodes;
	}

	/**
	 * Genero el arco (y,x) que representa el flujo de datos desde el lado derecho, y,
	 * al lado izquierdo, x, de la asignacion
	 * Si el lado derecho es un variable access, y tendra una referencia a una variable
	 * Si el lado derecho es un constructor invocation, y ser el objeto creado cs.this
	 * Si el lado derecho es un method invocation y sera una referencia al objeto retornado
	 * por el metodo, m.return
	 * Agrega tambien los arcos generados por el lado izquierdo y por el lado derecho  
	 */
	@Override
	public Map<String, Object> getOFGEdges() {

		ASNamedElement leftElement = (ASNamedElement)this.getLeftSide();
		ASNamedElement rightElement = (ASNamedElement)this.getRightSide();
		Map<String,Object> result = new HashMap<String,Object>();

		//si alguno de los dos lados es primitive se trata de una asignacion de tipo primitivo
		boolean isPrimitive = ((ASElement)rightElement).isPrimitive() || ((ASElement)leftElement).isPrimitive(); 
		if (!isPrimitive) {
			String leftSideNodeId = leftElement.getFullyScopedName();
			String rightSideNodeId = rightElement.getFullyScopedName();
			String edgeId = this.getEdgeId(rightSideNodeId, leftSideNodeId);
			ASElement value = rightSide;
			result.put(edgeId, value);	
		}
		result.putAll((leftSide).getOFGEdges());
		result.putAll((rightSide).getOFGEdges());
		
		return result;
	}

	@Override
	public boolean isPrimitive() {
		return rightSide.isPrimitive();
	}
	
	
}
