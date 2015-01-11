package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Collections;
import java.util.Map;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;


public class ASEmptyElement extends ASElement implements ASNamedElement {

	public ASEmptyElement() {
		isEmpty = true;
	}
	
	@Override
	public Map<String, OFGNode> getOFGNodes() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, Object> getOFGEdges() {
		return Collections.emptyMap();
	}

	@Override
	public String getFullyScopedName() {
		return "";
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}
	
	public String toString() {
		return "ASEmptyElement";
	}

}
