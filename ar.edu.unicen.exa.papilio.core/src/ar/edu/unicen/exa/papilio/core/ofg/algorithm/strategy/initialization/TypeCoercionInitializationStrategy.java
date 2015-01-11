package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class TypeCoercionInitializationStrategy extends
		FlowPropagationSetInitializationStrategy<Type> {

	@Override
	protected Set<Type> generateSet(OFGNode node) {
		
		Set<Type> result = new HashSet<Type>();
		for (OFGEdge edge : node.getEdges()) {
			if (edge.getValue() != null) {
				ASElement element = (ASElement) edge.getValue();
				if (element.getTypeCoercion() != null) {
					Type nodeType = element.getTypeCoercion();
					result.add(nodeType);
				}
			}
		}
		return result;
	}

	@Override
	protected Map<OFGNode, Set<Type>> endSetGeneration(
			Map<OFGNode, Set<Type>> initialMap) {

		return initialMap;
	}

}
