package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.initialization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.ClassInstanceCreation;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.ParameterizedType;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;

import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 *
 */
public class ObjectIdentifierInitializationStrategy extends FlowPropagationSetInitializationStrategy<InstanceSpecification>{

	
	private Map<Type,Integer> nextObjectIdentifier = new HashMap<Type,Integer>();
	private Map<Type, Set<InstanceSpecification>> instances = new HashMap<Type, Set<InstanceSpecification>>();
	
	/**
	 * Estrategia que inicializa aquellos nodos que conforman el lado izquierdo de sentencias de creacion 
	 * Por cada objeto instanciado se crea un elemento InstanceSpecification con el identificador del objeto asignado al nodo. 
	 * El identificador esta formado por el nombre de la clase c mas un entero incremental i
	 **/
	@Override
	protected Set<InstanceSpecification> generateSet(OFGNode node) {
		
		Set<InstanceSpecification> result = new HashSet<InstanceSpecification>();
		if (isConstructorInvocationLeftSideNode(node)) {
			OFGEdge classCreationEdge = getClassCreationEdge(node);
			ASConstructorInvocationStatement asInvocation = (ASConstructorInvocationStatement)classCreationEdge.getValue();
			ClassInstanceCreation creationExpression = (ClassInstanceCreation)asInvocation.getNode();
			Type instanceType = getExpressionType(creationExpression);
			if(instanceType != null) {
				InstanceSpecification newInstance = createInstance(instanceType, creationExpression);
				newInstance.getUsages().add(creationExpression);
				addInstance(instanceType, newInstance);
				result.add(newInstance);
			}
			
		} else if (isEnumConstantDeclaration(node)) {
			EnumConstantDeclaration enumConstantNode = (EnumConstantDeclaration)node.getASElement().getNode();
			//Deberia obtenerlo a traves de AbstractTypeDeclaration pero por alguna razon no setea esa referencia
			Type instanceType = (EnumDeclaration)enumConstantNode.eContainer();
			if(instanceType != null) {
				InstanceSpecification newInstance = createInstance(instanceType, null);
				addInstance(instanceType, newInstance);
				result.add(newInstance);
			}
		} 
		
		return result;
	}
	
	/**
	 * Obtiene el tipo del objeto creado por la expresion
	 * Si el tipo es un tipo parametrizado, retorna el raw type
	 */
	private Type getExpressionType(ClassInstanceCreation creationExpression) {
		Type type = creationExpression.getType().getType();
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getType().getType();
		}
		return type;
	}
	
	private boolean isEnumConstantDeclaration(OFGNode node) {
		
		return node.getASElement().getNode() instanceof EnumConstantDeclaration;
	}
	
	
	/**
	 * En caso de que el nodo sea el lado izquierdo de una sentencia de creacion
	 * retorna el arco correspondiente a la invocacion del constructor (cs.this,x)  
	 * @param targetNode El nodo para el que se desea obtener el arco
	 * @return El arco de creacion si el nodo constituye el lado izquierdo de una
	 * sentencia de creacion, null en caso contrario
	 */
	private OFGEdge getClassCreationEdge(OFGNode targetNode) {
		
		for (OFGEdge edge: targetNode.getPredecesors()) {
			if (edge.getSourceNode().getASElement() instanceof ASConstructorDeclaration)
				return edge;
		}
		return null;
	}
	
	/**
	 * Crea una nueva instancia para el tipo pasado como parametro
	 * @param instanceType El tipo para el que se desea genera rla instancia
	 * @param creationExpression La expresion de creacion de la instancia (en caso de tratarse de una sentencia de creacion)
	 * @return El elemento InstanceSpecification correspondiente a la instancia creada
	 */
	private InstanceSpecification createInstance (Type instanceType, Expression creationExpression) {
	
		//obtengo el identificador para el objeto
		String objectIdentifier = instanceType.getName();
		Integer objectSufix = nextObjectIdentifier.get(instanceType);
		if(objectSufix == null){
			objectSufix = new Integer("1");
		}
		objectIdentifier+=objectSufix;
		objectSufix++;
		nextObjectIdentifier.put(instanceType, objectSufix);
		
		//creo la instancia para el objeto
		InstanceSpecification newInstance = JavaFactory.eINSTANCE.createInstanceSpecification();
		newInstance.setName(objectIdentifier);
		newInstance.setInstanceType(instanceType);
		if (creationExpression != null) {
			newInstance.setCreationExpression(creationExpression);
		}
		
		return newInstance;
		
	}
	
	private void addInstance(Type instanceClass, InstanceSpecification instance){
		
		Set<InstanceSpecification> classInstances = instances.get(instanceClass);
		if (classInstances == null) {
			classInstances = new HashSet<InstanceSpecification>();
		}
		classInstances.add(instance);
		instances.put(instanceClass, classInstances);
	}

	@Override
	protected Map<OFGNode, Set<InstanceSpecification>> endSetGeneration (
			Map<OFGNode, Set<InstanceSpecification>> initialMap) {
		
		//para todos los nodos que representan la instancia actual this
		//agrego las instancias generadas para esa clase al conjunto inicial del nodo
		for(OFGNode node: initialMap.keySet()) {
			if (node.getASElement().isThisTarget()) {
				AbstractTypeDeclaration type = (AbstractTypeDeclaration)node.getASElement().getNode();
				Set<InstanceSpecification> initialSet = instances.get(type);
				if (!(initialSet == null)) {
					initialMap.put(node, initialSet);
				}
			}
		}
		
		return initialMap;
	}
	
}
