package ar.edu.unicen.exa.papilio.core.ofg.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.FlowPropagationSetInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.PropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class FlowPropagationAlgorithm<T> {
	
	public interface FlowPropagationAlgorithmListener<T> {

		void onAlgorithmStep(Map<OFGNode, Set<T>> set);
	}
	
	protected PropagationDirectionStrategy propagationDirection;
	
	protected Map<OFGNode, Set<T>> in, out, gen, kill;
	
	protected FlowPropagationSetInitializationStrategy<T> inInitializationStrategy, outInitializationStrategy, genInitializationStrategy, killInitializationStrategy;
	
	protected PropagationStrategy<T> propagationStrategy;

	public Map<OFGNode, Set<T>> getGen() {
		return gen;
	}

	public Map<OFGNode, Set<T>> getIn() {
		return in;
	}

	public void setIn(Map<OFGNode, Set<T>> in) {
		this.in = in;
	}

	public Map<OFGNode, Set<T>> getOut() {
		return out;
	}

	public void setOut(Map<OFGNode, Set<T>> out) {
		this.out = out;
	}
	
	public void initialize(OFGGraph graph, FlowPropagationAlgorithmListener<T> listener) {
		gen = genInitializationStrategy.initializeSet(graph);
		in = inInitializationStrategy.initializeSet(graph);
		kill = killInitializationStrategy.initializeSet(graph);
		out = outInitializationStrategy.initializeSet(graph);
		listener.onAlgorithmStep(gen);
	}

	/**
	 * Metodo que implementa el algoritmo de Flow Propagation sobre el OFG
	 * @param graph El OFG sobre el que se ejecuta el algoritmo
	 */
	public
	void propagate(OFGGraph graph, FlowPropagationAlgorithmListener<T> listener) {
		boolean converge = false;		
		
		while (!converge) {
			
			Map<OFGNode, Set<T>> currentIn = new HashMap<OFGNode, Set<T>>(in);
			Map<OFGNode, Set<T>> currentOut = new HashMap<OFGNode, Set<T>>(out);

			converge = true;
			for(OFGNode node: graph.getNodes()) {
				//Calcula el conjunto in para cada nodo
				//in[n] = U out[p], para todo p, p E pred(n) (forward)
				//							     p E suc(n)  (backward)
				Set<T> currentNodeIn = new HashSet<T>(in.get(node));
				Set<T> newIn = new HashSet<T>(getIncomingNodesOut(node));
				if(!currentNodeIn.equals(newIn)){
					currentIn.put(node, newIn);
					converge = false;
				}
				//Calcula el conjunto out para cada nodo
				//out[n] = gen[n] U (in[n] - kill[n])
				newIn.removeAll(new HashSet<T>(kill.get(node)));
				Set<T> newOut = new HashSet<T>(gen.get(node)); 
				newOut.addAll(newIn);
				Set<T> currentNodeOut = new HashSet<T>(out.get(node));
				if(!currentNodeOut.equals(newOut)){
					currentOut.put(node, newOut);
					converge = false;
				}
			}
			in = new HashMap<OFGNode, Set<T>>(currentIn);
			out = new HashMap<OFGNode, Set<T>>(currentOut);
		}
		listener.onAlgorithmStep(out);
	}
	
	public void applyChanges() {
		for (OFGNode node : out.keySet()) {
			this.propagationStrategy.propagate(node, out.get(node));
		}
	}

	/**
	 * Obtiene la union de los conjuntos out para los nodos de entrada de un nodo
	 * @param node El nodo para el que se desea obtener el conjunto mencionado 
	 * @return El conjunto conformado por la union de la salida
	 * (out) de todos los nodos de entrada 
	 */
	private Set<T> getIncomingNodesOut(OFGNode node){
		Set<T> result = new HashSet<T>();
		List<OFGEdge> incomingEdges = propagationDirection.getIncomingEdges(node); 
		for(OFGEdge edge: incomingEdges){
			OFGNode incomingNode = propagationDirection.getAdjacentNode(edge);
			result.addAll(out.get(incomingNode));
		}
		return result;
	}

}
