package ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.type;

import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.ForwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.AllocationTypeInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;

public class AllocationTypePropagationAlgorithm extends
		FlowPropagationAlgorithm<Type> {

	public AllocationTypePropagationAlgorithm() {
		this.propagationDirection = new ForwardPropagationStrategy();
		this.genInitializationStrategy = new AllocationTypeInitializationStrategy();
		this.inInitializationStrategy = new EmptyInitializationStrategy<Type>();
		this.outInitializationStrategy = new EmptyInitializationStrategy<Type>();
		this.killInitializationStrategy = new EmptyInitializationStrategy<Type>();
	}

}
