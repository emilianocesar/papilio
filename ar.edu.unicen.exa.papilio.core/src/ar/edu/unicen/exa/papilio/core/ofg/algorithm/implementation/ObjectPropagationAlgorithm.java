package ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation;

import org.eclipse.gmt.modisco.java.InstanceSpecification;

import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.ForwardPropagationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.EmptyInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization.ObjectIdentifierInitializationStrategy;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation.ObjectIdentifierPropagationStrategy;

public class ObjectPropagationAlgorithm extends FlowPropagationAlgorithm<InstanceSpecification> {

	public ObjectPropagationAlgorithm() {
		this.propagationDirection = new ForwardPropagationStrategy();
		this.genInitializationStrategy = new ObjectIdentifierInitializationStrategy();
		this.inInitializationStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		this.outInitializationStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		this.killInitializationStrategy = new EmptyInitializationStrategy<InstanceSpecification>();
		this.propagationStrategy = new ObjectIdentifierPropagationStrategy();
	}

	@Override
	public void initialize(
			OFGGraph graph,
			FlowPropagationAlgorithm.FlowPropagationAlgorithmListener<InstanceSpecification> listener) {
		super.initialize(graph, listener);
	}
	
}
