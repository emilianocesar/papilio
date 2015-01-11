package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.Collections;
import java.util.Map;

public class ASVariableAccess extends ASStatement implements ASNamedElement {

	ASAttributeDeclaration variableDeclaration;
	
	public ASAttributeDeclaration getVariableDeclaration() {
		return variableDeclaration;
	}

	public void setVariableDeclaration(ASAttributeDeclaration variableDeclaration) {
		this.variableDeclaration = variableDeclaration;
	}

	/**
	 * Retorna un mapa vacio ya que una referencia a una variable
	 * por si sola no agrega arcos al ofg 
	 */
	@Override
	public Map<String, Object> getOFGEdges() {
		return Collections.emptyMap();
	}
	
	public String toString() {
		return this.variableDeclaration.toString();
	}

	@Override
	public String getFullyScopedName() {
		return this.variableDeclaration.getFullyScopedName();
	}

	@Override
	public boolean isPrimitive() {
		return variableDeclaration.isPrimitive();
	}

}
