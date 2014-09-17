/*
 * NOTICE: THE FILE HAS BEEN MODIFIED TO SUIT THE NEEDS OF THE PROJECT.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.robobinding.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Migrated some of methods from
 * {@link org.apache.commons.lang3.reflect.MethodUtils}.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MethodUtils {

	private MethodUtils() {
		super();
	}

	/**
	 * <p>
	 * Returns an accessible method (that is, one that can be invoked via
	 * reflection) with given name and parameters. If no such method can be
	 * found, return <code>null</code>. This is just a convenient wrapper for
	 * {@link #getAccessibleMethod(Method method)}.
	 * </p>
	 * 
	 * @param cls
	 *            get method from this class
	 * @param methodName
	 *            get method with this name
	 * @param parameterTypes
	 *            with these parameters types
	 * @return The accessible method
	 */
	public static Method getAccessibleMethod(final Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
		try {
			return getAccessibleMethod(cls.getMethod(methodName, parameterTypes));
		} catch (final NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * Returns an accessible method (that is, one that can be invoked via
	 * reflection) that implements the specified Method. If no such method can
	 * be found, return <code>null</code>.
	 * </p>
	 * 
	 * @param method
	 *            The method that we wish to call
	 * @return The accessible method
	 */
	public static Method getAccessibleMethod(Method method) {
		if (!MemberUtils.isAccessible(method)) {
			return null;
		}
		// If the declaring class is public, we are done
		final Class<?> cls = method.getDeclaringClass();
		if (Modifier.isPublic(cls.getModifiers())) {
			return method;
		}
		final String methodName = method.getName();
		final Class<?>[] parameterTypes = method.getParameterTypes();

		// Check the implemented interfaces and subinterfaces
		method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes);

		// Check the superclass chain
		if (method == null) {
			method = getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
		}
		return method;
	}

	/**
	 * <p>
	 * Returns an accessible method (that is, one that can be invoked via
	 * reflection) by scanning through the superclasses. If no such method can
	 * be found, return <code>null</code>.
	 * </p>
	 * 
	 * @param cls
	 *            Class to be checked
	 * @param methodName
	 *            Method name of the method we wish to call
	 * @param parameterTypes
	 *            The parameter type signatures
	 * @return the accessible method or <code>null</code> if not found
	 */
	private static Method getAccessibleMethodFromSuperclass(final Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
		Class<?> parentClass = cls.getSuperclass();
		while (parentClass != null) {
			if (Modifier.isPublic(parentClass.getModifiers())) {
				try {
					return parentClass.getMethod(methodName, parameterTypes);
				} catch (final NoSuchMethodException e) {
					return null;
				}
			}
			parentClass = parentClass.getSuperclass();
		}
		return null;
	}

	/**
	 * <p>
	 * Returns an accessible method (that is, one that can be invoked via
	 * reflection) that implements the specified method, by scanning through all
	 * implemented interfaces and subinterfaces. If no such method can be found,
	 * return <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * There isn't any good reason why this method must be private. It is
	 * because there doesn't seem any reason why other classes should call this
	 * rather than the higher level methods.
	 * </p>
	 * 
	 * @param cls
	 *            Parent class for the interfaces to be checked
	 * @param methodName
	 *            Method name of the method we wish to call
	 * @param parameterTypes
	 *            The parameter type signatures
	 * @return the accessible method or <code>null</code> if not found
	 */
	private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
		Method method = null;

		// Search up the superclass chain
		for (; cls != null; cls = cls.getSuperclass()) {

			// Check the implemented interfaces of the parent class
			final Class<?>[] interfaces = cls.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				// Is this interface public?
				if (!Modifier.isPublic(interfaces[i].getModifiers())) {
					continue;
				}
				// Does the method exist on this interface?
				try {
					method = interfaces[i].getDeclaredMethod(methodName, parameterTypes);
				} catch (final NoSuchMethodException e) { // NOPMD
					/*
					 * Swallow, if no method is found after the loop then this
					 * method returns null.
					 */
				}
				if (method != null) {
					break;
				}
				// Recursively check our parent interfaces
				method = getAccessibleMethodFromInterfaceNest(interfaces[i], methodName, parameterTypes);
				if (method != null) {
					break;
				}
			}
		}
		return method;
	}

	/**
	 * <p>
	 * Finds an accessible method that matches the given name and has compatible
	 * parameters. Compatible parameters mean that every method parameter is
	 * assignable from the given parameters. In other words, it finds a method
	 * with the given name that will take the parameters given.
	 * <p>
	 * 
	 * <p>
	 * This method is used by
	 * {@link #invokeMethod(Object object, String methodName, Object[] args, Class[] parameterTypes)}.
	 * 
	 * <p>
	 * This method can match primitive parameter by passing in wrapper classes.
	 * For example, a <code>Boolean</code> will match a primitive
	 * <code>boolean</code> parameter.
	 * 
	 * @param cls
	 *            find method in this class
	 * @param methodName
	 *            find method with this name
	 * @param parameterTypes
	 *            find method with most compatible parameters
	 * @return The accessible method
	 */
	public static Method getMatchingAccessibleMethod(final Class<?> cls, final String methodName, final Class<?>... parameterTypes) {
		try {
			final Method method = cls.getMethod(methodName, parameterTypes);
			MemberUtils.setAccessibleWorkaround(method);
			return method;
		} catch (final NoSuchMethodException e) { // NOPMD - Swallow the
			// exception
		}
		// search through all methods
		Method bestMatch = null;
		final Method[] methods = cls.getMethods();
		for (final Method method : methods) {
			// compare name and parameters
			if (method.getName().equals(methodName) && ClassUtils.isAssignable(parameterTypes, method.getParameterTypes(), true)) {
				// get accessible version of method
				final Method accessibleMethod = getAccessibleMethod(method);
				if (accessibleMethod != null
						&& (bestMatch == null || MemberUtils.compareParameterTypes(accessibleMethod.getParameterTypes(), bestMatch.getParameterTypes(),
								parameterTypes) < 0)) {
					bestMatch = accessibleMethod;
				}
			}
		}
		if (bestMatch != null) {
			MemberUtils.setAccessibleWorkaround(bestMatch);
		}
		return bestMatch;
	}
}
