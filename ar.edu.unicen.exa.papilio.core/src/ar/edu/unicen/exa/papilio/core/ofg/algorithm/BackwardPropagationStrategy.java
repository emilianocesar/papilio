package ar.edu.unicen.exa.papilio.core.ofg.algorithm;

import java.util.List;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * Implementa la estrategia de propagacion backward
 * En esta estrategia los nodos entrantes son los sucesores o
 * adyacentes del nodo n
 * 
 * @author Belen Rolandi
 *
 */
public class BackwardPropagationStrategy extends PropagationDirectionStrategy {

	@Override
	public List<OFGEdge> getIncomingEdges(OFGNode node) {
		return node.getEdges();
	}

	@Override
	public OFGNode getAdjacentNode(OFGEdge edge) {
		return edge.getTargetNode();
	}

}
