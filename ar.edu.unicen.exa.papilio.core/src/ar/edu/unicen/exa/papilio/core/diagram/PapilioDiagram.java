package ar.edu.unicen.exa.papilio.core.diagram;

import ar.edu.unicen.exa.papilio.core.ofg.algorithm.FlowPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.ObjectPropagationAlgorithm;
import ar.edu.unicen.exa.papilio.core.ofg.algorithm.implementation.TypePropagationAlgorithm;

public enum PapilioDiagram {
	CLASS(new TypePropagationAlgorithm(), "class"),
	SEQUENCE(new ObjectPropagationAlgorithm(), "sequence"),
	USECASES(null, "usecase");
	
	private FlowPropagationAlgorithm<?> algorithm;
	private String name;

	private PapilioDiagram(FlowPropagationAlgorithm<?> algorithm, String name) {
		this.algorithm = algorithm;
		this.name = name;
	}
	
	public FlowPropagationAlgorithm<?> getAlgorithm() {
		return this.algorithm;
	}

	public String getName() {
		return name;
	}
	
}
