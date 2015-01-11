package ar.edu.unicen.exa.papilio.core.ofg.algorithm;

import java.util.List;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * Define la interfaz para implementar estrategias de direccionamiento
 * para el algoritmo de propagacion
 * Las estrategias de direccionamiento difieren en la forma de obtener los
 * arcos de entrada a partir de los cuales se genera el conjunto in de un nodo
 * en cada propagacion   
 * 
 * @author Belen Rolandi
 */
public abstract class PropagationDirectionStrategy {
	
	public abstract List<OFGEdge> getIncomingEdges(OFGNode node);
	public abstract OFGNode getAdjacentNode(OFGEdge edge);

}
