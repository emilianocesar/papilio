/**
 * 
 */
package ar.edu.unicen.exa.papilio.core.as.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gmt.modisco.java.MethodInvocation;

/**
 * @author Belen Rolandi
 * @created 18/07/2012
 */
public class ASMethodInvocationStatement extends ASStatement implements ASNamedElement {

	private ASMethodDeclaration declaration;
	private ASNamedElement target;
	private List<ASNamedElement> arguments = new ArrayList<ASNamedElement>();

		
	public ASMethodDeclaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(ASMethodDeclaration declaration) {
		this.declaration = declaration;
	}

	public ASNamedElement getTarget() {
		return target;
	}

	public void setTarget(ASNamedElement target) {
		this.target = target;
	}

	public List<ASNamedElement> getArguments() {
		return arguments;
	}

	public void setArguments(List<ASNamedElement> arguments) {
		this.arguments = arguments;
	}
	

	public String toString() {
		
		MethodInvocation invocation = (MethodInvocation)node;
		StringBuilder methodInvocationString = new StringBuilder(target.toString());
		methodInvocationString.append(".");
		methodInvocationString.append(invocation.getMethod().getName());
		methodInvocationString.append("(");
		
		if (arguments.size() > 0) {
			
			methodInvocationString.append(arguments.get(0).toString());
			
			for(int i=1; i<arguments.size(); i++) {
				
				methodInvocationString.append(",");
				methodInvocationString.append(arguments.get(i).toString());
			}
		}
		
		methodInvocationString.append(")");
		
		return methodInvocationString.toString();
	}
	
	/**
	 * Agrego el arco (y, m.this) que relaciona el metodo invocado con el target
	 * de la invocacion
	 * Luego agrego la lista de arcos (ai, fi) relacionando cada parametro formal
	 * con su respectivo parametro real 
	 */
	@Override
	public Map<String, Object> getOFGEdges() {
		
		if(this.isPrimitive()) {
			//si se trata de una invocacion a un metodo primitivo, no genero arcos
			return Collections.emptyMap();
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		ASElement target = (ASElement)this.getTarget();
		if (!target.isPrimitive() && !target.isEmpty()) {
			//Si el target no es primitivo y no es vacio, agrego el arco(y,m.this)
			String targetId = this.getTarget().getFullyScopedName();
			StringBuilder methodDeclarationId = new StringBuilder(this.getDeclaration().getFullyScopedName());
			methodDeclarationId.append(".this");
			String targetEdgeId = this.getEdgeId(targetId, methodDeclarationId.toString());
			//Agrego como valor del arco el elemento actual
			result.put(targetEdgeId, this);
		}
		
		//Agrego el arco (ai,fi) para cada parametro
		List<ASNamedElement> realParameters = this.getArguments();
		List<ASAttributeDeclaration> formalParameters = declaration.getFormalParameters();
		
		for (int i = 0; i < realParameters.size(); i++) {
			ASElement argument = (ASElement)realParameters.get(i);
			ASElement parameter = (ASElement)formalParameters.get(i);
			//si el argumento o el parametro formal corresponden a un tipo primitivo no genero el arco
			boolean isPrimitive = parameter.isPrimitive() || argument.isPrimitive();
			if (!argument.isEmpty() && !isPrimitive) {
				String realParameterNodeId = realParameters.get(i).getFullyScopedName();
				String formalParameterNodeId = formalParameters.get(i).getFullyScopedName();
				String paramEdgeId = this.getEdgeId(realParameterNodeId, formalParameterNodeId);
				//Agrego como valor del arco el elemento asociado al parametro real
				ASElement paramEdgeValue = argument;
				result.put(paramEdgeId, paramEdgeValue);
			}
		}
		
		return result;
	}

	@Override
	public String getFullyScopedName() {
		StringBuilder fullName = new StringBuilder(this.declaration.getFullyScopedName());
		if (this.getDeclaration().returnsObject()) {
			fullName.append(".return");
		} else {
			fullName.append(".this");
		}
		return fullName.toString();
	}

	@Override
	public boolean isPrimitive() {
		return declaration.isPrimitive();
	}

	
}
