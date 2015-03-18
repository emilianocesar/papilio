/**
 */
package parameters;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see parameters.ParametersPackage
 * @generated
 */
public interface ParametersFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ParametersFactory eINSTANCE = parameters.impl.ParametersFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>param</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>param</em>'.
	 * @generated
	 */
	param createparam();

	/**
	 * Returns a new object of class '<em>parameter Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>parameter Container</em>'.
	 * @generated
	 */
	parameterContainer createparameterContainer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ParametersPackage getParametersPackage();

} //ParametersFactory
