package ar.edu.unicen.exa.papilio.core.diagram;

import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.ObjectPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.TypePropagationAlgorithm;

public enum PapilioDiagram {
	CLASS(new TypePropagationAlgorithm()),
	SEQUENCE(new ObjectPropagationAlgorithm()),
	USECASES(null);
	
	private FlowPropagationAlgorithm<?> algorithm;

	private PapilioDiagram(FlowPropagationAlgorithm<?> algorithm) {
		this.algorithm = algorithm;
	}
	
	public FlowPropagationAlgorithm<?> getAlgorithm() {
		return this.algorithm;
	}
	
}
