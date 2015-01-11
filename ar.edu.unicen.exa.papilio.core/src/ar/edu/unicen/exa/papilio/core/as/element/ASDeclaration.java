package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Collections;
import java.util.Map;

/**
 * @author Belen Rolandi
 *
 */
public abstract class ASDeclaration extends ASElement implements ASNamedElement {
	
	protected String fullyScopedName;
	protected boolean isPrimitive;

	public String getFullyScopedName() {
		return fullyScopedName;
	}

	public void setFullyScopedName(String fullyScopedName) {
		this.fullyScopedName = fullyScopedName;
	}

	public String toString() {
		return fullyScopedName;
	}
	
	public Map<String, Object> getOFGEdges() {
		return Collections.emptyMap();
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public void setPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

}
