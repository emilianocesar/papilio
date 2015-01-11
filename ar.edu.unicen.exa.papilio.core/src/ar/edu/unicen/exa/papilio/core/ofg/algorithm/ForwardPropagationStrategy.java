package ar.edu.unicen.exa.papilio.core.ofg.algorithm;

import java.util.List;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * Implementa la estrategia de propagacion forward
 * En esta estrategia los nodos entrantes son los predecesores del nodo n
 * 
 * @author Belen Rolandi
 *
 */
public class ForwardPropagationStrategy extends PropagationDirectionStrategy {

	@Override
	public List<OFGEdge> getIncomingEdges(OFGNode node) {
		return node.getPredecesors();
	}

	@Override
	public OFGNode getAdjacentNode(OFGEdge edge) {
		return edge.getSourceNode();
	}

}
