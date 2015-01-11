package ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.type.AllocationTypePropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.type.TypeCoercionPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.TypePropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class TypePropagationAlgorithm extends FlowPropagationAlgorithm<Type> {

	AllocationTypePropagationAlgorithm allocationPropagationAlgorithm;
	TypeCoercionPropagationAlgorithm typeCoercionPropagationAlgorithm;

	public TypePropagationAlgorithm() {
		this.allocationPropagationAlgorithm = new AllocationTypePropagationAlgorithm();
		this.typeCoercionPropagationAlgorithm = new TypeCoercionPropagationAlgorithm();
		this.propagationStrategy = new TypePropagationStrategy();
	}

	public void initialize(
			Model model,
			OFGGraph graph,
			FlowPropagationAlgorithm.FlowPropagationAlgorithmListener<Type> listener) {
		((TypePropagationStrategy)this.propagationStrategy).setJavaModel(model);
		final Map<OFGNode, Set<Type>> firstGenSet = new HashMap<>();
		final Map<OFGNode, Set<Type>> secondGenSet = new HashMap<>();
		this.allocationPropagationAlgorithm.initialize(graph,
				new FlowPropagationAlgorithmListener<Type>() {

					@Override
					public void onAlgorithmStep(Map<OFGNode, Set<Type>> set) {
						firstGenSet.putAll(set);
					}
				});
		
		this.typeCoercionPropagationAlgorithm.initialize(graph,
				new FlowPropagationAlgorithmListener<Type>() {

					@Override
					public void onAlgorithmStep(Map<OFGNode, Set<Type>> set) {
						secondGenSet.putAll(set);
					}
				});
		
		listener.onAlgorithmStep(unionSet(firstGenSet, secondGenSet));
	}
	

	@Override
	public void propagate(OFGGraph graph,
			FlowPropagationAlgorithmListener<Type> listener) {
		final Map<OFGNode, Set<Type>> firstOutSet = new HashMap<>();
		final Map<OFGNode, Set<Type>> secondOutSet = new HashMap<>();
		this.allocationPropagationAlgorithm.propagate(graph, new FlowPropagationAlgorithmListener<Type>() {

			@Override
			public void onAlgorithmStep(Map<OFGNode, Set<Type>> set) {
				firstOutSet.putAll(set);
			}
		});
		
		this.typeCoercionPropagationAlgorithm.initialize(graph,new FlowPropagationAlgorithmListener<Type>() {

			@Override
			public void onAlgorithmStep(Map<OFGNode, Set<Type>> set) {
				secondOutSet.putAll(set);
			}
		});
		this.out = unionSet(firstOutSet, secondOutSet);
		listener.onAlgorithmStep(this.out);
	}

	public Map<OFGNode, Set<Type>> unionSet(Map<OFGNode, Set<Type>> firstSet,
			Map<OFGNode, Set<Type>> secondSet) {
		Set<OFGNode> keySet = firstSet.keySet();
		for (OFGNode ofgNode : keySet) {
			Set<Type> secondSetTypes = secondSet.get(ofgNode);
			firstSet.get(ofgNode).addAll(secondSetTypes);
		}
		return firstSet;
	}
}
