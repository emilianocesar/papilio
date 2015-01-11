package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.InstanceSpecification;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.emf.provider.JavaItemProviderAdapterFactory;

import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

/**
 * @author Belen Rolandi
 * Clase que se encarga de guardar en el modelo el resultado de la propagacion 
 * de ids de objetos realizado a traves del ofg
 * Guarda las instancias creadas en la lista instances de cada clase
 * Por cada invocacion de metodo realizada sobre un objeto, guarda una 
 * referencia a la invocacion en la lista usages de la instancia 
 */
public class ObjectIdentifierPropagationStrategy extends PropagationStrategy<InstanceSpecification>{

	
	@Override
	public void propagate(OFGNode node, Set<InstanceSpecification> out){

		for (InstanceSpecification instance : out){
			
			if (!isStoredInstance(instance.getInstanceType(), instance)){
				//la instancia no fue agregada a la clase
				//agrego la instancia usando un comando EMF
				Type instanceType = instance.getInstanceType();
				EStructuralFeature instancesFeature = instanceType.eClass().getEStructuralFeature("instances");
				addValueToListFeature(instanceType, instancesFeature, instance);
			}
		
		//obtengo la lista de invocaciones de metodo asociadas al nodo 
		List<MethodInvocation> invocations = this.getMethodInvocations(node);

		//agrego las invocaciones de metodo asociadas al nodo, a la lista
		//usages de la instancia
		for (MethodInvocation invocation : invocations) {
			//agrego la invocacion a la lista usages de la instancia
			EStructuralFeature usagesFeature = instance.eClass().getEStructuralFeature("usages");
			addValueToListFeature(instance, usagesFeature, invocation);
		}
			
	}
		
}
	
	/**
	 * Obtiene la lista de invocaciones de metodo asociadas al nodo pasado como parametro
	 * @param node El nodo para el que se desea obtener las invocaciones asociadas
	 * @return La lista de invocaciones realizadas sobre el nodo en caso de ser el
	 * target de alguna invocacion. Si el nodo no es target de ninguna invocacion
	 * retorna una lista vacia
	 */
	private List<MethodInvocation> getMethodInvocations(OFGNode node) {
		List<MethodInvocation>resultInvocations = new ArrayList<MethodInvocation>();
		for(OFGEdge adjacentEdge: node.getEdges()) {
			OFGNode adjacent = adjacentEdge.getTargetNode();
			if (isMethodDeclaration(adjacent)){
				ASMethodInvocationStatement asInvocation = (ASMethodInvocationStatement)adjacentEdge.getValue();
				MethodInvocation invocation = (MethodInvocation)asInvocation.getNode();
				resultInvocations.add(invocation);
			}
		}
		
		return resultInvocations;
	}
	
	/**
	 * Un nodo representa una declaracion de metodo si el ASElement asociaco es de tipo
	 * ASMethodDeclaration y ademas su id termina en .this
	 * @param node El node para el que se desea efectuar la verificacion
	 * @return True si el nodo representa una declaracion de metodo, False en caso contrario
	 */
	private boolean isMethodDeclaration(OFGNode node) {
		boolean isMethodDeclaration = node.getASElement() instanceof ASMethodDeclaration;
		String patternString = ".this";
		return isMethodDeclaration && node.getId().endsWith(patternString);
		
	}
	
	/**
	 * Chequeo si la instancia pasada como parametro ya se encuentra almacenada
	 * en la lista instances de su clase
	 * @param instanceClass
	 * @param instance
	 * @return
	 */
	private boolean isStoredInstance(Type instanceType, InstanceSpecification instance){
		
		for(InstanceSpecification storedInstance : instanceType.getInstances()){
			if(storedInstance.getName().equals(instance.getName())){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Agrega un valor a una propiedad de tipo multiple de un nodo en el modelo java
	 * La insercion se realiza utilizando un comando EMF
	 * @param owner El nodo que posee la propiedad para la cual se desea insertar el valor
	 * @param feature La propiedad de tipo multiple
	 * @param value El valor que se desea insertar
	 */
	private void addValueToListFeature(ASTNode owner, EStructuralFeature feature, Object value){
		
		BasicCommandStack commandStack = new BasicCommandStack();
		AdapterFactory javaItemAdapterFactory = new JavaItemProviderAdapterFactory();
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(javaItemAdapterFactory, commandStack);
		Command addCommand = AddCommand.create(editingDomain, owner, feature, value);
		editingDomain.getCommandStack().execute(addCommand);
	}
	
}
