package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation;

import java.util.Set;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public abstract class PropagationStrategy<T> {
	
	public abstract void propagate(OFGNode node, Set<T> out);

}
