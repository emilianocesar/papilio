package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.Type;

import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class AllocationTypeInitializationStrategy extends
		FlowPropagationSetInitializationStrategy<Type> {

	/**
	 * Estrategia que inicializa el conjunto con el tipo asociado a un nodo
	 * Obtiene el tipo solo para los nodos de creacion, ya que inicialmente
	 * solo estos nodos saben cual es su tipo real, en los demas casos devuelve
	 * un conjunto vacio 
	 */
	@Override
	public Set<Type> generateSet(OFGNode node) {
		
		Set<Type >initialTypes = new HashSet<Type>();
	
		if (isConstructorInvocationLeftSideNode(node)) {
			
			List<ASConstructorDeclaration> constructors = getConstructorDeclarations(node);
			
			for (ASConstructorDeclaration constructor : constructors) {
			
				ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration)constructor.getNode();
				Type nodeType = constructorDeclaration.getAbstractTypeDeclaration();
				initialTypes.add(nodeType);
			}
		}
		return initialTypes;
	}
	
	
	/**
	 * Obtiene la declaracion de constructor asociada al nodo pasado como parametro 
	 */
	public List<ASConstructorDeclaration> getConstructorDeclarations(OFGNode node){
		
		List<ASConstructorDeclaration> result = new ArrayList<ASConstructorDeclaration>();
		
		for(OFGEdge predEdge : node.getPredecesors()){
			OFGNode pred = predEdge.getSourceNode();
			if(pred.getASElement() instanceof ASConstructorDeclaration)
				result.add((ASConstructorDeclaration)pred.getASElement());
		}
		return result;
	}

	@Override
	protected Map<OFGNode, Set<Type>> endSetGeneration(
			Map<OFGNode, Set<Type>> initialMap) {
		return initialMap;
	}
}
