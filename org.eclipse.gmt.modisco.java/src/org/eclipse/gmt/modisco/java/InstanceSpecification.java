/**
 * *******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastien Minguet (Mia-Software) - initial API and implementation
 *     Frederic Madiot (Mia-Software) - initial API and implementation
 *     Fabien Giquel (Mia-Software) - initial API and implementation
 *     Gabriel Barbier (Mia-Software) - initial API and implementation
 *     Erwan Breton (Sodifrance) - initial API and implementation
 *     Romain Dervaux (Mia-Software) - initial API and implementation
 * *******************************************************************************
 */
package org.eclipse.gmt.modisco.java;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance Specification</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.gmt.modisco.java.InstanceSpecification#getInstanceType <em>Instance Type</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.InstanceSpecification#getUsages <em>Usages</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.InstanceSpecification#getCreationExpression <em>Creation Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.gmt.modisco.java.emf.JavaPackage#getInstanceSpecification()
 * @model
 * @generated
 */
public interface InstanceSpecification extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Instance Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Type</em>' reference.
	 * @see #setInstanceType(Type)
	 * @see org.eclipse.gmt.modisco.java.emf.JavaPackage#getInstanceSpecification_InstanceType()
	 * @model
	 * @generated
	 */
	Type getInstanceType();

	/**
	 * Sets the value of the '{@link org.eclipse.gmt.modisco.java.InstanceSpecification#getInstanceType <em>Instance Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Type</em>' reference.
	 * @see #getInstanceType()
	 * @generated
	 */
	void setInstanceType(Type value);

	/**
	 * Returns the value of the '<em><b>Usages</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.gmt.modisco.java.AbstractMethodInvocation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usages</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usages</em>' reference list.
	 * @see org.eclipse.gmt.modisco.java.emf.JavaPackage#getInstanceSpecification_Usages()
	 * @model
	 * @generated
	 */
	EList<AbstractMethodInvocation> getUsages();

	/**
	 * Returns the value of the '<em><b>Creation Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Expression</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Expression</em>' reference.
	 * @see #setCreationExpression(Expression)
	 * @see org.eclipse.gmt.modisco.java.emf.JavaPackage#getInstanceSpecification_CreationExpression()
	 * @model
	 * @generated
	 */
	Expression getCreationExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.gmt.modisco.java.InstanceSpecification#getCreationExpression <em>Creation Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Expression</em>' reference.
	 * @see #getCreationExpression()
	 * @generated
	 */
	void setCreationExpression(Expression value);

} // InstanceSpecification
