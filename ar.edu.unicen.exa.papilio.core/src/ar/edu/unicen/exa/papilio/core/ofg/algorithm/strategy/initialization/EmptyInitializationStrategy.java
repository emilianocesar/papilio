package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class EmptyInitializationStrategy<T> extends
		FlowPropagationSetInitializationStrategy<T> {

	@Override
	public Set<T> generateSet(OFGNode node) {
		return new HashSet<T>();
	}

	@Override
	protected Map<OFGNode, Set<T>> endSetGeneration(
			Map<OFGNode, Set<T>> initialMap) {
		return initialMap;
	}

	
}
