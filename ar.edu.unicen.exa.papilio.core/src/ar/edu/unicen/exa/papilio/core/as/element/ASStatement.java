package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Collections;
import java.util.Map;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 *
 */
public abstract class ASStatement extends ASElement {
	

	public Map<String, OFGNode> getOFGNodes() {
		return Collections.emptyMap();
	}
}
