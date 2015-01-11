package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.InterfaceDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.ParameterizedType;
import org.eclipse.gmt.modisco.java.PrimitiveType;
import org.eclipse.gmt.modisco.java.PrimitiveTypeVoid;
import org.eclipse.gmt.modisco.java.SingleVariableAccess;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.TypeAccess;
import org.eclipse.gmt.modisco.java.VariableDeclaration;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;

import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.CollectionTypes;
import ar.edu.unicen.exa.papilio.core.as.Context;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException.ASTranslatorExceptionLevel;

/**
 * @author Belen Rolandi
 * 
 */
public abstract class AbstractTranslator implements AbstractSyntaxTranslator {

	protected static final String COLLECTION_INSERTION_METHOD_ADD = "add";
	protected static final String MAP_INSERTION_METHOD_PUT = "put";
	protected static final String QUEUE_INSERTION_METHOD_OFFER = "offer";

	protected static final String QUEUE_EXTRACTION_METHOD_ELEMENT = "element";
	protected static final String QUEUE_EXTRACTION_METHOD_PEEK = "peek";
	protected static final String QUEUE_EXTRACTION_METHOD_POLL = "poll";
	protected static final String QUEUE_EXTRACTION_METHOD_REMOVE = "remove";
	protected static final String COLLECTION_EXTRACTION_METHOD_GET = "get";
	protected static final String MAP_EXTRACTION_METHOD_GET = "get";
	protected static final String ITERATOR_EXTRACTION_METHOD_NEXT = "next";
	protected static final String ITERATOR_INTERFACE_DECLARATION_NAME = "Iterator";

	protected static final String ITERATOR_INVOCATION = "iterator";

	protected Context context;

	/**
	 * Metodo encargado de traducir el elemento pasado como parametro a la
	 * sintaxis abstracta. Delega en las subclases la traduccion del elemento
	 * dependiendo de su tipo
	 */
	public abstract List<ASElement> translate(ASTNode node);

	public ASTNode getEnclosedNode(ASTNode node) {
		return node;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}

	public AbstractSyntaxTranslator getTranslatorForNode(ASTNode node) {
		AbstractSyntaxTranslator translator = TranslatorFactory.INSTANCE
				.getTranslator(node);
		translator.setContext(this.context);
		return translator;
	}

	/**
	 * Retorna el nombre completo de la expression this pasada como parametro El
	 * nombre completo de una expresion this esta dado por la clase
	 * que contiene la expresion, es decir, el class scope indicado por el
	 * contexto
	 * 
	 * @return El nombre completo de la expresion this pasada como parametro
	 */
	protected String getThisExpressionName() {

		StringBuilder thisExpressionFullName = new StringBuilder(
				this.context.getClassScope());
		thisExpressionFullName.append(".this");
		return thisExpressionFullName.toString();
	}

	/**
	 * Obtiene el tipo del target de la invocacion, es decir, el tipo al que
	 * pertenece el objeto sobre el que se invoca el metodo
	 * 
	 * @param invocation
	 *            La invocacion de metodo para la que se desea obtener el tipo
	 *            del target
	 * @return El tipo del target de la invocacion pasada como parametro
	 * 
	 */
	protected Type getInvocationTargetType(MethodInvocation invocation) {

		Expression expression = invocation.getExpression();

		return getExpressionType(expression);
	}

	/**
	 * Si la expression es tal que contiene una variable, este metodo extrae su
	 * type.
	 * 
	 * @param expression
	 * @return
	 * 
	 */
	protected Type getExpressionType(Expression expression) {
		Type type = null;
		if (expression instanceof SingleVariableAccess) {

			VariableDeclaration variable = ((SingleVariableAccess) expression)
					.getVariable();

			if (variable instanceof VariableDeclarationFragment) {
				
				TypeAccess containerType = ((VariableDeclarationFragment) variable)
						.getVariablesContainer().getType();
				if (containerType != null) {
					type = containerType.getType();
				}
				
			} else if (variable instanceof SingleVariableDeclaration) {
					type = ((SingleVariableDeclaration) variable).getType()
							.getType();
			} else if (variable instanceof EnumConstantDeclaration) {
					type = (EnumDeclaration) variable.eContainer();
			} else {
				ASProgram.INSTANCE.getErrors().add(new ASTranslatorException(
						"Could not translate expression type", variable,
						ASTranslatorExceptionLevel.ERROR));
			}
			
		}

		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getType().getType();
		}

		return type;
	}

	/**
	 * Verifica si el tipo pasado como parametro es una coleccion Un tipo es una
	 * coleccion, si es una interfaz perteneciente al Java Collection Framework
	 * o es una clase que implementa alguna de las interfaces del Collection
	 * Framework
	 * 
	 * @param targetType
	 *            El tipo que se desea verificar
	 * @return True si el tipo es una coleccion, false en caso contrario
	 */
	protected Boolean isCollectionType(Type targetType) {
		
		if (targetType instanceof ParameterizedType) {
			targetType = ((ParameterizedType) targetType).getType().getType();
		}

		if (targetType instanceof InterfaceDeclaration) {

			return isCollectionInterface((InterfaceDeclaration) targetType);

		} else {
			// chequeo si alguna de las interfaces que implementa es una
			// collection interface
			if (targetType instanceof ClassDeclaration) {

				ClassDeclaration clase = (ClassDeclaration) targetType;
				if (clase.getSuperInterfaces() != null) {
					for (TypeAccess interfaceType : clase.getSuperInterfaces()) {
						if (interfaceType.getType() instanceof ParameterizedType) {
							interfaceType = ((ParameterizedType)interfaceType.getType()).getType();
						}
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
	 * Verifica si una interfaz pasada como parametro es alguna de las
	 * interfaces dentro del Java Collection Framework Las interfaces que
	 * conforman el Java Collection Framework son: Collection, Set, SortedSet,
	 * List, Queue, Map y SortedMap
	 * 
	 * @param type
	 *            La interfaz que se desea verificar
	 * @return true si la interfaz es una coleccion, false en caso contrario
	 */
	private Boolean isCollectionInterface(InterfaceDeclaration type) {

		String typeName = type.getName();

		return typeName.equals(CollectionTypes.COLLECTION.getName())
				|| typeName.equals(CollectionTypes.LIST.getName())
				|| typeName.equals(CollectionTypes.MAP.getName())
				|| typeName.equals(CollectionTypes.SORTED_MAP.getName())
				|| typeName.equals(CollectionTypes.SET.getName())
				|| typeName.equals(CollectionTypes.SORTED_SET.getName())
				|| typeName.equals(CollectionTypes.QUEUE.getName());
	}

	/**
	 * Verifica si la MethodInvocation pasada como parametro es una invocacion
	 * de un metodo de insercion sobre una coleccion Para esto se debe cumplir
	 * que el target object sea una coleccion y ademas que el metodo invocado
	 * sea un metodo de insercion
	 * 
	 * @param invocation
	 *            Elemento de tipo Methodnvocation para el que se desea realizar
	 *            la verificacion
	 * @return True si la invocacion pasada como parametro es un metodo de
	 *         insercion en una coleccion, false en caso contrario
	 * 
	 */
	protected Boolean isContainerInsertion(MethodInvocation invocation) {

		Type targetType = this.getInvocationTargetType(invocation);
		
		if(targetType == null)
			return false;

		Boolean isCollectionType = isCollectionType(targetType);
		Boolean isInsertion = invocation.getMethod().getName()
				.equals(COLLECTION_INSERTION_METHOD_ADD)
				|| invocation.getMethod().getName()
						.equals(MAP_INSERTION_METHOD_PUT)
				|| invocation.getMethod().getName()
						.equals(QUEUE_INSERTION_METHOD_OFFER);

		return isCollectionType && isInsertion;
	}

	/**
	 * Indica si el nodo pasado como parametro corresponde a la asignacion de un
	 * iterador para un contenedor Esto es si la expresion en el lado derecho es
	 * un MethodInvocation, y el target de la invocacion es una coleccion, y el
	 * metodo invocado retorna un iterator
	 * 
	 * @param invocation
	 *            La invocacion que se desea valuar
	 * @return True si el assignment corresponde a la asignacion de un iterator,
	 *         False en caso contrario
	 * 
	 */
	protected Boolean isContainerIteratorInvocation(MethodInvocation invocation) {
		Type targetType = this.getInvocationTargetType(invocation);
		return isCollectionType(targetType)
				&& isIteratorInvocationMethod(invocation);
	}

	/**
	 * Indica si la invocacion pasada como parametro es un metodo que obtiene un
	 * iterador para un contenedor (i.e. metodo iterator())
	 * 
	 * @param invocation
	 *            La invocacion para la que se desea verificar el tipo
	 * @return True si el metodo devuelve un iterador para un contenedor, False
	 *         en caso contrario
	 */
	private boolean isIteratorInvocationMethod(MethodInvocation invocation) {
		return invocation.getMethod().getName().equals(ITERATOR_INVOCATION);
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
	 * Verifica si el metodo pasado como parametro corresponde al metodo de
	 * iteracion (extraccion) de un Iterator (i.e. metodo next())
	 * 
	 * @param invocation
	 *            La invocacion que se desea verificar
	 * @return True si la invocacion corresponde a un metodo de iteracion, false
	 *         en caso contrario
	 */
	private boolean isIteratorExtractionMethod(
			AbstractMethodDeclaration methodDeclaration) {
		return methodDeclaration.getName().equals(
				ITERATOR_EXTRACTION_METHOD_NEXT);
	}

	/**
	 * Verifica si la invocacion de metodo pasada como parametro representa una
	 * operacion de extraccion sobre un contenedor Se trata de una extraccion si
	 * el target de la invocacion es una coleccion y el metodo invocado es un
	 * metodo de extraccion (i.e. get) o si el target es un iterator y el metodo
	 * invocado es un metodo de iteracion (i.e. next)
	 * 
	 * @param assignment
	 *            El assignment sobre el que se desea realizar la verificacion
	 * @return True si el assignment pasado como parametro representa una
	 *         extraccion sobre un contenedor, False en caso contrario
	 * 
	 */
	protected Boolean isContainerExtractionInvocation(
			MethodInvocation invocation) {
		Type targetType = this.getInvocationTargetType(invocation);
		return isCollectionType(targetType)
				&& isContainerExtractionMethod(invocation)
				|| isIteratorType(targetType)
				&& isIteratorExtractionMethod(invocation.getMethod());
	}

	/**
	 * Verifica si la invocacion pasada como parametro representa un metodo de
	 * extraccion sobre algun contenedor
	 * 
	 * @param invocation
	 *            La invocacion que se desea verificar
	 * @return True si la invocacion corresponde a un metodo de extraccion sobre
	 *         un contenedor, false en caso contrario
	 * 
	 */
	protected boolean isContainerExtractionMethod(MethodInvocation invocation) {
		Type targetType = this.getInvocationTargetType(invocation);
		Boolean isCollectionType = isCollectionType(targetType);
		Boolean isExtractionMethod = invocation.getMethod().getName()
				.equals(COLLECTION_EXTRACTION_METHOD_GET)
				|| invocation.getMethod().getName()
						.equals(MAP_EXTRACTION_METHOD_GET)
				|| invocation.getMethod().getName()
						.equals(QUEUE_EXTRACTION_METHOD_ELEMENT)
				|| invocation.getMethod().getName()
						.equals(QUEUE_EXTRACTION_METHOD_PEEK)
				|| invocation.getMethod().getName()
						.equals(QUEUE_EXTRACTION_METHOD_POLL)
				|| invocation.getMethod().getName()
						.equals(QUEUE_EXTRACTION_METHOD_REMOVE);
		return isCollectionType && isExtractionMethod;
	}
	
	
	/**
	 * Verifica si el tipo pasado como parametro corresponde a un tipo primitivo
	 * Se considera que un tipo es un tipo primitivo si es alguno de los tipos
	 * primitivos java distinto de void
	 * o corresponde a alguna de las clases wrapper 
	 * @param type El tipo que se desea verificar
	 * @return True si type es un tipo primitivo, false en caso contrario
	 */
	protected boolean isPrimitiveType(Type type) {
		
		return (type instanceof PrimitiveType && !(type instanceof PrimitiveTypeVoid))
				|| isWrapperType(type);
	}

	/**
	 * Verifica si el tipo pasado como parametro corresponde a alguna de las 
	 * clases wrapper de un tipo primitivo
	 * Las clases wrapper son Boolean, Character, Number y String y se encuentran
	 * dentro del paquete java.lang 
	 * @param type El tipo que se desea verificar
	 * @return True si type es un wrapper, false en caso contrario
	 */
	protected boolean isWrapperType(Type type) {

		if (type instanceof ClassDeclaration) {
			ClassDeclaration classType = (ClassDeclaration)type;
			String fullPackageName = getFullPackageName(classType.getPackage());
			return fullPackageName.equals("java.lang")
					&& (classType.getName().equals("Boolean")
							|| classType.getName().equals("Character")
							|| classType.getName().equals("String") 
							|| isNumberClass(classType));
		}
		return false;
	}
	
	private String getFullPackageName(Package pack) {
		
		if (pack == null)
			return "";
		if (pack.getPackage() == null) {
			return pack.getName();
		} else {
			String name = pack.getName();
			return getFullPackageName(pack.getPackage()) + "." + name;
		}
	}
	
	private boolean isNumberClass(ClassDeclaration classDeclaration) {
		
		TypeAccess superClass = classDeclaration.getSuperClass();
		if (superClass != null) {
			return superClass.getType().getName().equals("Number");
		}
		return false;
	}
}