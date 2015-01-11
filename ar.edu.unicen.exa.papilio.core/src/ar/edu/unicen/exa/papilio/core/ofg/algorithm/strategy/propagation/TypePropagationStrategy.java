package ar.edu.unicen.exa.papilio.core.ofg.algorithm.strategy.propagation;

import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.InterfaceDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.ParameterizedType;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.eclipse.gmt.modisco.java.emf.JavaFactory;
import org.eclipse.gmt.modisco.java.emf.provider.JavaItemProviderAdapterFactory;

import ar.edu.unicen.exa.papilio.core.as.CollectionTypes;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class TypePropagationStrategy extends PropagationStrategy<Type> {

	private static final String ITERATOR_INTERFACE_DECLARATION_NAME = "Iterator";
	private final int DEFAULT_ARGUMENT_INDEX = 0;
	private final int MAP_ARGUMENT_INDEX = 1;
	
	//mantengo una referencia al modelo para agregar los elementos generados
	private Model javaModel;
	
	
	public Model getJavaModel() {
		return javaModel;
	}

	public void setJavaModel(Model javaModel) {
		this.javaModel = javaModel;
	}

	@Override
	public void propagate(OFGNode node, Set<Type> out) {
		if (!out.isEmpty()) {
			ASTNode javaNode = node.getASElement().getNode();
			Type type = this.getLeastCommonAncestor(out);
			this.applyChange(javaNode, type);
		}
	}

	private void applyChange(ASTNode astNode, Type type) {

		if (isTypedElement(astNode)) {
			TypeAccess nodeTypeAccess = getNodeTypeAccess(astNode);
			if (nodeTypeAccess != null) {
				Type nodeType = nodeTypeAccess.getType();
				if (!(nodeType instanceof ParameterizedType)) {
					if (isCollectionType(nodeType) || isIteratorType(nodeType)) {
						ParameterizedType paramType = getSingleArgumentParamType(
								nodeType, type);
							setFeature("type", nodeTypeAccess, paramType);
						
						} else if (isMapType(nodeType)) {
							ParameterizedType mapType = getMapParamType(
									nodeType, type);
								setFeature("type", nodeTypeAccess, mapType);
					} else {
						if (!(nodeTypeAccess.getType().equals(type))) {
							setFeature("type", nodeTypeAccess, type); 
						}
					}
				}
			}
		}
	}
	
	private void setFeature(String featureName, EObject featureOwner,
			EObject value) {
		EStructuralFeature feature = featureOwner.eClass()
				.getEStructuralFeature(featureName);
		BasicCommandStack commandStack = new BasicCommandStack();
		AdapterFactory javaItemAdapterFactory = new JavaItemProviderAdapterFactory();
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
				javaItemAdapterFactory, commandStack);
		Command setCommand = SetCommand.create(editingDomain, featureOwner,
				feature, value);
		editingDomain.getCommandStack().execute(setCommand);
	}

	/**
	 * Verifica si el ASTNode pasado como parametro pertenece a una de las
	 * clases que poseen un atributo type
	 * 
	 * @param astNode
	 *            El nodo que se desea verificar
	 * @return True si el nodo es un typed element, false en caso contrario
	 */
	// TODO verificar si hay que incluir alguna otra clase aca
	private boolean isTypedElement(ASTNode astNode) {

		return astNode instanceof SingleVariableDeclaration
				|| astNode instanceof VariableDeclarationFragment;
	}

	/**
	 * Obtiene el tipo correspondiente al typed element pasado como parametro
	 * Los posibles typed elements considerados son SingleVariableDeclaration
	 * (parametro) y VariableDeclarationFragment. Dado que un
	 * VariableDeclarationFragment constituye un fragmento dentro de una
	 * declaracion de variable local o de clase, para acceder al tipo es
	 * necesario obtener la informacion de la declaracion, es decir el
	 * VariablesContainer en que se encuentra definida
	 * 
	 * @param astNode
	 *            El nodo para el que se desea obtener el tipo
	 * @return El elemento TypeAccess que posee una referencia al tipo actual
	 *         del nodo
	 */
	private TypeAccess getNodeTypeAccess(ASTNode astNode) {

		if (astNode instanceof SingleVariableDeclaration) {

			return ((SingleVariableDeclaration) astNode).getType();

		} else if (astNode instanceof VariableDeclarationFragment) {

			return ((VariableDeclarationFragment) astNode)
					.getVariablesContainer().getType();

		} else
			// en la practica este metodo nunca va a retornar null, dado que
			// previamente se verifico que el nodo sea un typed element
			return null;
	}

	private Type getLeastCommonAncestor(Set<Type> out) {
		if (out.size() > 1) {
			// TODO hacer esta verga
		} else {
			if (out.size() == 1) {
				return (Type) out.iterator().next();
			}
		}
		return null;
	}
	
	/**
	 * Verifica si ya existe en el modelo un tipo parametrizado que coincida
	 * con el tipo base y parametro indicados
	 * @param baseType El tipo base
	 * @param parameterType El tipo del parametro
	 * @param paramIndex El indice en que se encuentra el parametro a verificar
	 * @return Si el tipo parametrizado ya existe en el modelo lo retorna, en caso
	 * contrario retorna null
	 */
	private ParameterizedType getExistingParamType(Type baseType, Type parameterType, int paramIndex) {
		for (Type type : javaModel.getOrphanTypes()) {
			if (type instanceof ParameterizedType) {
				ParameterizedType currentType = (ParameterizedType) type;
				if ((currentType.getType().getType()).equals(baseType)
						&& currentType.getTypeArguments().get(paramIndex).getType()
								.equals(parameterType)) {
					return currentType;
				}
			}
		}
		return null;
	}
	
	private ParameterizedType getSingleArgumentParamType(Type baseType, Type parameterType) {
	//verifica si ya existe en el modelo el tipo parametrizado, si no existe lo crea
		ParameterizedType paramType = this.getExistingParamType(baseType,
				parameterType, this.DEFAULT_ARGUMENT_INDEX);
		if (paramType == null) {
			paramType = createSingleArgumentParamType(baseType, parameterType);
		}
		return paramType;
	}
	
	private ParameterizedType getMapParamType(Type baseType, Type parameterType) {
		//verifica si ya existe en el modelo el mapa parametrizado, si no existe lo crea
			ParameterizedType paramType = this.getExistingParamType(baseType,
					parameterType, this.MAP_ARGUMENT_INDEX);
			if (paramType != null) {
				//verifico si el key type es Object
				Type keyType = paramType.getTypeArguments().get(0).getType();
				if (keyType.getName().equals("Object")) {
					return paramType;
				} 
			} 
			return createMapType(baseType, parameterType);
		}
	
	/**
	 * Crea un nuevo tipo parametrizado java con un solo argumento
	 * @param baseType El tipo base que se desea parametrizar
	 * @param parameterType El tipo del argumento 
	 * @return El nuevo tipo parametrizado con baseType como tipo y parameterType
	 * como argumento
	 */
	private ParameterizedType createSingleArgumentParamType(
			Type baseType, Type parameterType) {
		
		//creo tipo parametrizado
		ParameterizedType paramType = JavaFactory.eINSTANCE
				.createParameterizedType();
		
		//seteo como tipo el tipo original
		TypeAccess baseTypeAccess = JavaFactory.eINSTANCE.createTypeAccess();
		baseTypeAccess.setType(baseType);
		paramType.setType(baseTypeAccess);
		
		//obtengo nombre para el tipo parametrizado
		String paramTypeName = buildParamTypeName(baseType,
				parameterType);
		paramType.setName(paramTypeName);
		
		//creo argumento para el tipo de los objetos
		TypeAccess paramTypeAccess = JavaFactory.eINSTANCE
					.createTypeAccess();
		//seteo el tipo propagado como type del argumento
		paramTypeAccess.setType(parameterType);
		
		//agrego argumento para el tipo parametrizado
		paramType.getTypeArguments().add(paramTypeAccess);
		
		//agrego el tipo creado al modelo
		javaModel.getOrphanTypes().add(paramType);

		return paramType;
	}
	
	/**
	 * Crea un mapa parametrizado con baseType como tipo base
	 * Object como keyType y parameterType como valueType
	 * @param baseType El tipo que se desea parametrizar
	 * @param parameterType El tipo para los elementos (values) almacenados en el mapa
	 * @return El nuevo mapa parametrizado
	 */
	private ParameterizedType createMapType(Type baseType,
			Type parameterType) {
		
		//creo tipo parametrizado
		ParameterizedType paramType = JavaFactory.eINSTANCE
				.createParameterizedType();
		
		//seteo como tipo el mapa original
		TypeAccess baseTypeAccess = JavaFactory.eINSTANCE.createTypeAccess();
		baseTypeAccess.setType(baseType);
		paramType.setType(baseTypeAccess);

		//creo argumento para el tipo de los objetos almacenados
		TypeAccess valueTypeAccess = JavaFactory.eINSTANCE
				.createTypeAccess();
		//seteo el tipo propagado como tipo del argumento
		valueTypeAccess.setType(parameterType);
		
		//obtengo la clase object para el valor del parametro key 
		AbstractTypeDeclaration objectClass = this.getObjectClass();
		
		//creo argumento para el parametro key
		TypeAccess keyTypeAccess = JavaFactory.eINSTANCE.createTypeAccess();
		keyTypeAccess.setType(objectClass);
		
		//obtengo el nombre para el mapa parametrizado
		String mapName = buildMapName(baseType, objectClass,
				parameterType);
		//seteo el nombre
		paramType.setName(mapName);
		
		//seteo argumentos para el tipo parametrizado
		paramType.getTypeArguments().add(keyTypeAccess);
		paramType.getTypeArguments().add(valueTypeAccess);
		
		//agrego el tipo creado al modelo
		javaModel.getOrphanTypes().add(paramType);
		
		return paramType;
	}
	
	private String buildParamTypeName(Type baseType, Type paramType) {
		
		StringBuilder paramTypeName = new StringBuilder(baseType.getName());
		paramTypeName.append("<");
		paramTypeName.append(paramType.getName());
		paramTypeName.append(">");
		return paramTypeName.toString();
		
	}
	
	private String buildMapName(Type baseType, Type keyType, Type valueType) {
		
		StringBuilder mapName = new StringBuilder(baseType.getName());
		mapName.append("<");
		mapName.append(keyType.getName());
		mapName.append(",");
		mapName.append(valueType.getName());
		mapName.append(">");
		return mapName.toString();
	}
	
	private AbstractTypeDeclaration getObjectClass() {
		
		//obtengo el paquete java
		Package javaPackage = this.getPackageByName("java", javaModel.getOwnedElements());
		if(javaPackage != null) {
			//obtengo el paquete lang
			Package langPackage = this.getPackageByName("lang", javaPackage.getOwnedPackages());
			if (langPackage != null) {
				//obtengo la clase object
				AbstractTypeDeclaration objectClass = this.getTypeByName(
						"Object", langPackage.getOwnedElements());
				return objectClass;
			}
		}
		return null;
	}
	
	private Package getPackageByName(String packageName, EList<Package>packages) {
		
		for (Package pack : packages) {
			if(pack.getName().equals(packageName)) {
				return pack;
			}
		}
		return null;
	}
	
	private AbstractTypeDeclaration getTypeByName(String typeName, EList<AbstractTypeDeclaration> elements) {
		
		for (AbstractTypeDeclaration type : elements) {
			if (type.getName().equals(typeName)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Verifica si el tipo pasado como parametro es una coleccion 
	 * Un tipo es una coleccion si se trata de la interfaz Collection o una de sus
	 * subinterfaces, o si es una clase que implementa alguna de las interfaces
	 * de tipo Collection
	 * 
	 * @param type
	 *            El tipo que se desea verificar
	 * @return true, si el tipo es una coleccion, false en caso contrario
	 */
	private boolean isCollectionType(Type type) {
		if (type instanceof InterfaceDeclaration) {
			return isCollectionInterface((InterfaceDeclaration) type);
		} else {
			// chequeo si alguna de las interfaces que implementa es una
			// collection interface
			if (type instanceof ClassDeclaration) {
				ClassDeclaration clase = (ClassDeclaration) type;
				if (clase.getSuperInterfaces() != null) {
					for (TypeAccess interfaceType : clase.getSuperInterfaces()) {
						InterfaceDeclaration interfaz = (InterfaceDeclaration) interfaceType
								.getType();
						Boolean isCollection = isCollectionInterface(interfaz);
						if (isCollection)
							return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Verifica si el tipo pasado como parametro es un mapa 
	 * Un tipo es un map si es la interfaz Map o una de sus subinterfaces 
	 * o si se trata de una clase que implementa alguna de las interfaces Map
	 * 
	 * @param type
	 *            El tipo que se desea verificar
	 * @return true si el tipo es un mapa, false en caso contrario
	 */
	private boolean isMapType(Type type) {
		if (type instanceof InterfaceDeclaration) {
			return isMapInterface((InterfaceDeclaration) type);
		} else {
			// chequeo si alguna de las interfaces que implementa es un
			// map
			if (type instanceof ClassDeclaration) {
				ClassDeclaration clase = (ClassDeclaration) type;
				if (clase.getSuperInterfaces() != null) {
					for (TypeAccess interfaceType : clase.getSuperInterfaces()) {
						InterfaceDeclaration interfaz = (InterfaceDeclaration) interfaceType
								.getType();
						Boolean isMap = isMapInterface(interfaz);
						if (isMap)
							return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifica si el tipo pasado como parametro corresponde a la interfaz
	 * Iterator
	 * 
	 * @param type
	 *            El tipo que se desea verificar
	 * @return True si el type es un Iterator, False en cao contrario
	 */
	private boolean isIteratorType(Type type) {
		if (!(type instanceof InterfaceDeclaration))
			return false;
		InterfaceDeclaration interfaceDeclaration = (InterfaceDeclaration) type;
		return interfaceDeclaration.getName().equals(
				ITERATOR_INTERFACE_DECLARATION_NAME);
	}

	/**
	 * Verifica si la interfaz pasada como parametro corresponde a la interfaz
	 * Collection o a una interfaz que implementa la interfaz Collection
	 * 
	 * @param type
	 *            La interfaz que se desea verificar
	 * @return true si la interfaz es una coleccion, false en caso contrario
	 */
	private boolean isCollectionInterface(InterfaceDeclaration type) {
		String typeName = type.getName();
		return typeName.equals(CollectionTypes.COLLECTION.getName())
				|| typeName.equals(CollectionTypes.LIST.getName())
				|| typeName.equals(CollectionTypes.SET.getName())
				|| typeName.equals(CollectionTypes.SORTED_SET.getName())
				|| typeName.equals(CollectionTypes.QUEUE.getName());
	}

	/**
	 * Verifica si la interfaz pasada como parametro corresponde a la interfaz
	 * Map o a alguna de sus subinterfaces
	 * 
	 * @param type
	 *            La interfaz que se desea verficar
	 * @return true si la interfaz es un map, false en caso contrario
	 */
	private boolean isMapInterface(InterfaceDeclaration type) {
		String typeName = type.getName();
		return typeName.equals(CollectionTypes.MAP.getName())
				|| typeName.equals(CollectionTypes.SORTED_MAP.getName());
	}

}
