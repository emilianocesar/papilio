package ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.type;

import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.ofg.algorithm.BackwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.TypeCoercionInitializationStrategy;

public class TypeCoercionPropagationAlgorithm extends
		FlowPropagationAlgorithm<Type> {

	public TypeCoercionPropagationAlgorithm() {
		this.propagationDirection = new BackwardPropagationStrategy();
		this.genInitializationStrategy = new TypeCoercionInitializationStrategy();
		this.inInitializationStrategy = new EmptyInitializationStrategy<Type>();
		this.outInitializationStrategy = new EmptyInitializationStrategy<Type>();
		this.killInitializationStrategy = new EmptyInitializationStrategy<Type>();
	}
}
