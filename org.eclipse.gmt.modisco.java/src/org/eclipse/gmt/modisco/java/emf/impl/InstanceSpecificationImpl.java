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
package org.eclipse.gmt.modisco.java.emf.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.gmt.modisco.java.AbstractMethodInvocation;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ClassInstanceCreation;
import org.eclipse.gmt.modisco.java.Expression;
import org.eclipse.gmt.modisco.java.InstanceSpecification;

import org.eclipse.gmt.modisco.java.Type;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instance Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.gmt.modisco.java.emf.impl.InstanceSpecificationImpl#getInstanceType <em>Instance Type</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.emf.impl.InstanceSpecificationImpl#getUsages <em>Usages</em>}</li>
 *   <li>{@link org.eclipse.gmt.modisco.java.emf.impl.InstanceSpecificationImpl#getCreationExpression <em>Creation Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstanceSpecificationImpl extends NamedElementImpl implements InstanceSpecification {
	/**
	 * The cached value of the '{@link #getInstanceType() <em>Instance Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceType()
	 * @generated
	 * @ordered
	 */
	protected Type instanceType;

	/**
	 * The cached value of the '{@link #getUsages() <em>Usages</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsages()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractMethodInvocation> usages;

	/**
	 * The cached value of the '{@link #getCreationExpression() <em>Creation Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression creationExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstanceSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JavaPackage.eINSTANCE.getInstanceSpecification();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getInstanceType() {
		if (instanceType != null && instanceType.eIsProxy()) {
			InternalEObject oldInstanceType = (InternalEObject)instanceType;
			instanceType = (Type)eResolveProxy(oldInstanceType);
			if (instanceType != oldInstanceType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaPackage.INSTANCE_SPECIFICATION__INSTANCE_TYPE, oldInstanceType, instanceType));
			}
		}
		return instanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetInstanceType() {
		return instanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceType(Type newInstanceType) {
		Type oldInstanceType = instanceType;
		instanceType = newInstanceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.INSTANCE_SPECIFICATION__INSTANCE_TYPE, oldInstanceType, instanceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractMethodInvocation> getUsages() {
		if (usages == null) {
			usages = new EObjectResolvingEList<AbstractMethodInvocation>(AbstractMethodInvocation.class, this, JavaPackage.INSTANCE_SPECIFICATION__USAGES);
		}
		return usages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getCreationExpression() {
		if (creationExpression != null && creationExpression.eIsProxy()) {
			InternalEObject oldCreationExpression = (InternalEObject)creationExpression;
			creationExpression = (Expression)eResolveProxy(oldCreationExpression);
			if (creationExpression != oldCreationExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, JavaPackage.INSTANCE_SPECIFICATION__CREATION_EXPRESSION, oldCreationExpression, creationExpression));
			}
		}
		return creationExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression basicGetCreationExpression() {
		return creationExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationExpression(Expression newCreationExpression) {
		Expression oldCreationExpression = creationExpression;
		creationExpression = newCreationExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.INSTANCE_SPECIFICATION__CREATION_EXPRESSION, oldCreationExpression, creationExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JavaPackage.INSTANCE_SPECIFICATION__INSTANCE_TYPE:
				if (resolve) return getInstanceType();
				return basicGetInstanceType();
			case JavaPackage.INSTANCE_SPECIFICATION__USAGES:
				return getUsages();
			case JavaPackage.INSTANCE_SPECIFICATION__CREATION_EXPRESSION:
				if (resolve) return getCreationExpression();
				return basicGetCreationExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JavaPackage.INSTANCE_SPECIFICATION__INSTANCE_TYPE:
				setInstanceType((Type)newValue);
				return;
			case JavaPackage.INSTANCE_SPECIFICATION__USAGES:
				getUsages().clear();
				getUsages().addAll((Collection<? extends AbstractMethodInvocation>)newValue);
				return;
			case JavaPackage.INSTANCE_SPECIFICATION__CREATION_EXPRESSION:
				setCreationExpression((Expression)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JavaPackage.INSTANCE_SPECIFICATION__INSTANCE_TYPE:
				setInstanceType((Type)null);
				return;
			case JavaPackage.INSTANCE_SPECIFICATION__USAGES:
				getUsages().clear();
				return;
			case JavaPackage.INSTANCE_SPECIFICATION__CREATION_EXPRESSION:
				setCreationExpression((Expression)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JavaPackage.INSTANCE_SPECIFICATION__INSTANCE_TYPE:
				return instanceType != null;
			case JavaPackage.INSTANCE_SPECIFICATION__USAGES:
				return usages != null && !usages.isEmpty();
			case JavaPackage.INSTANCE_SPECIFICATION__CREATION_EXPRESSION:
				return creationExpression != null;
		}
		return super.eIsSet(featureID);
	}

} //InstanceSpecificationImpl
