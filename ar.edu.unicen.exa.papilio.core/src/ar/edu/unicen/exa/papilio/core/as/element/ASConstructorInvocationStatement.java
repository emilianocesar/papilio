/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gmt.modisco.java.ClassInstanceCreation;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public class ASConstructorInvocationStatement extends ASStatement implements ASNamedElement {

	private List<ASNamedElement> arguments = new ArrayList<ASNamedElement>();
	private ASConstructorDeclaration asContructorDeclaration;
	
	public void setConstructorDeclaration(
			ASConstructorDeclaration asConstructorDeclaration) {
		this.asContructorDeclaration = asConstructorDeclaration;
	}
	
	public ASConstructorDeclaration getConstructorDeclaration() {
		return this.asContructorDeclaration;
	}
	
	public List<ASNamedElement> getArguments() {
		return arguments;
	}

	public void setArguments(List<ASNamedElement> arguments) {
		this.arguments = arguments;
	}
	
	
	public String toString(){
		
		ClassInstanceCreation constructorInvocation = (ClassInstanceCreation)node;
		StringBuilder constructorInvocationString = new StringBuilder("new "+constructorInvocation.getType().getType().getName());
		constructorInvocationString.append("(");
		
		if (arguments.size() > 0) {
			
			constructorInvocationString.append(arguments.get(0).toString());
			
			for(int i=1; i<arguments.size(); i++) {
				
				constructorInvocationString.append(",");
				constructorInvocationString.append(arguments.get(i).toString());
			}
		}
		
		constructorInvocationString.append(")");
		return constructorInvocationString.toString();
	}


	/**
	 * Crea una lista de arcos (ai, fi) vinculando cada parametro real con su correspondiente
	 * parametro formal 
	 */
	@Override
	public Map<String, Object> getOFGEdges() {
		
		if (this.isPrimitive()) {
			return Collections.emptyMap();
		}

		Map<String, Object> result = new HashMap<String, Object>();
		
		//Agrego por cada parametro el arco (ai,fi)
						
		List<ASNamedElement> realParameters = this.getArguments();
		List<ASAttributeDeclaration> formalParameters = this.asContructorDeclaration.getFormalParameters();
				
		for (int i = 0; i < realParameters.size(); i++) {
			ASElement argument = (ASElement)realParameters.get(i);
			ASElement parameter = (ASElement)formalParameters.get(i);
			//si el argumento o el parametro formal corresponden a un tipo primitivo no genero el arco
			boolean isPrimitive = parameter.isPrimitive() || argument.isPrimitive();
			if (!argument.isEmpty() && !isPrimitive) {
			//si el argumento no es vacio genero el arco
				String realParameterNodeId = realParameters.get(i).getFullyScopedName();
				String formalParameterNodeId = formalParameters.get(i).getFullyScopedName();
				String edgeId = this.getEdgeId(realParameterNodeId, formalParameterNodeId);
				//Agrego como valor del arco el elemento asociado al parametro real
				ASElement value = argument;
				result.put(edgeId, value);
			}
		}
		
		return result;
	}

	@Override
	public String getFullyScopedName() {
		StringBuilder fullName = new StringBuilder(this.getConstructorDeclaration().getFullyScopedName());
		fullName.append(".this");
		return fullName.toString();
	}

	@Override
	public boolean isPrimitive() {
		return asContructorDeclaration.isPrimitive;
	}
}

