/**
 */
package parameters;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see parameters.ParametersFactory
 * @model kind="package"
 * @generated
 */
public interface ParametersPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "parameters";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://parameters/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "parameters";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ParametersPackage eINSTANCE = parameters.impl.ParametersPackageImpl.init();

	/**
	 * The meta object id for the '{@link parameters.impl.paramImpl <em>param</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see parameters.impl.paramImpl
	 * @see parameters.impl.ParametersPackageImpl#getparam()
	 * @generated
	 */
	int PARAM = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM__VALUE = 1;

	/**
	 * The number of structural features of the '<em>param</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>param</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link parameters.impl.parameterContainerImpl <em>parameter Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see parameters.impl.parameterContainerImpl
	 * @see parameters.impl.ParametersPackageImpl#getparameterContainer()
	 * @generated
	 */
	int PARAMETER_CONTAINER = 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_CONTAINER__PARAMETERS = 0;

	/**
	 * The number of structural features of the '<em>parameter Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>parameter Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_CONTAINER_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link parameters.param <em>param</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>param</em>'.
	 * @see parameters.param
	 * @generated
	 */
	EClass getparam();

	/**
	 * Returns the meta object for the attribute '{@link parameters.param#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see parameters.param#getName()
	 * @see #getparam()
	 * @generated
	 */
	EAttribute getparam_Name();

	/**
	 * Returns the meta object for the attribute '{@link parameters.param#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see parameters.param#getValue()
	 * @see #getparam()
	 * @generated
	 */
	EAttribute getparam_Value();

	/**
	 * Returns the meta object for class '{@link parameters.parameterContainer <em>parameter Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>parameter Container</em>'.
	 * @see parameters.parameterContainer
	 * @generated
	 */
	EClass getparameterContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link parameters.parameterContainer#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see parameters.parameterContainer#getParameters()
	 * @see #getparameterContainer()
	 * @generated
	 */
	EReference getparameterContainer_Parameters();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ParametersFactory getParametersFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link parameters.impl.paramImpl <em>param</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see parameters.impl.paramImpl
		 * @see parameters.impl.ParametersPackageImpl#getparam()
		 * @generated
		 */
		EClass PARAM = eINSTANCE.getparam();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAM__NAME = eINSTANCE.getparam_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAM__VALUE = eINSTANCE.getparam_Value();

		/**
		 * The meta object literal for the '{@link parameters.impl.parameterContainerImpl <em>parameter Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see parameters.impl.parameterContainerImpl
		 * @see parameters.impl.ParametersPackageImpl#getparameterContainer()
		 * @generated
		 */
		EClass PARAMETER_CONTAINER = eINSTANCE.getparameterContainer();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_CONTAINER__PARAMETERS = eINSTANCE.getparameterContainer_Parameters();

	}

} //ParametersPackage
