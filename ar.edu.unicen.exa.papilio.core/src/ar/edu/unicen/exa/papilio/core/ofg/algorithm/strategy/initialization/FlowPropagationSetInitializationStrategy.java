package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public abstract class FlowPropagationSetInitializationStrategy<T> {

	/**
	 * Crea el conjunto de datos iniciales para cada nodo del OFG
	 * de acuerdo a la estrategia que implementa la clase 
	 * @param graph El OFG sobre el que se ejecuta el algoritmo de propagacion
	 * @return Un mapa que asocia a cada nodo con el conjunto de elementos iniciales
	 * de acuerdo a la estrategia utilizada
	 */
	public Map<OFGNode, Set<T>> initializeSet(OFGGraph graph) {
		Map<OFGNode, Set<T>> result = new HashMap<OFGNode, Set<T>>();
		for(OFGNode node:graph.getNodes()) {
			result.put(node, this.generateSet(node));
		}
		Map<OFGNode, Set<T>>initialMap = endSetGeneration(result);
		return initialMap;
	}
	
	/**
	 * Metodo abstracto que permite obtener el conjunto inicial para
	 * un nodo de acuerdo a una estrategia espec�fica
	 * @param node El nodo del OFG para el que se desea obtener el conjunto inicial 
	 * @return El conjunto inicial correspondiente al nodo pasado como parametro 
	 */
	protected abstract Set<T> generateSet(OFGNode node);
	
	/**
	 * Finaliza la inicializacion del conjunto para cada nodo
	 * Se ejecuta luego de invocar al metodo generateSet sobre todos
	 * los nodos 
	 */
	protected abstract Map<OFGNode, Set<T>> endSetGeneration(Map<OFGNode, Set<T>>initialMap);


/**
 * Verifica si el nodo pasado como parametro constituye el lado
 * izquierdo de una sentencia de creacion
 * Un nodo constituye el lado izquierdo de una sentencia de creacion
 * si posee entre sus predecesores un nodo de tipo ASConstructorDeclaration
 */
protected Boolean isConstructorInvocationLeftSideNode(OFGNode node){
	
	for(OFGEdge predEdge : node.getPredecesors()){
		OFGNode pred = predEdge.getSourceNode();
		if(pred.getASElement() instanceof ASConstructorDeclaration)
			return true;
	}
	return false;
}

}
