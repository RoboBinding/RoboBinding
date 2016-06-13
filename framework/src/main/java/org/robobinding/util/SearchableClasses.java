package org.robobinding.util;

import java.util.Queue;
import java.util.Set;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SearchableClasses {
	private final Set<Class<?>> classes;

	public SearchableClasses(Set<Class<?>> classes) {
		this.classes = classes;
	}

	public Class<?> findNearestAssignableFrom(Class<?> clazz) {
		if ((clazz == Object.class) || (clazz == null)) {
			return null;
		}

		if (classes.contains(clazz)) {
			return clazz;
		}

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			Class<?> classFound = findNearestAssignableFrom(interfaceClass);
			if (classFound != null) {
				return classFound;
			}
		}

		return findNearestAssignableFrom(clazz.getSuperclass());
	}

	public Queue<Class<?>> findAssignablesInOrderFrom(Class<?> clazz) {
		Queue<Class<?>> assignablesInOrder = Lists.newLinkedList();

		findAssignablesInOrderFromClass(clazz, assignablesInOrder);
		return assignablesInOrder;
	}

	private void findAssignablesInOrderFromClass(Class<?> clazz, Queue<Class<?>> assignablesInOrder) {
		if (clazz == Object.class) {
			return;
		}

		if (classes.contains(clazz)) {
			assignablesInOrder.add(clazz);
		}

		findAssignablesInOrderFromInterface(clazz.getInterfaces(), assignablesInOrder);
		findAssignablesInOrderFromClass(clazz.getSuperclass(), assignablesInOrder);
	}

	private void findAssignablesInOrderFromInterface(Class<?>[] interfaceClasses, Queue<Class<?>> assignablesInOrder) {
		for (Class<?> interfaceClass : interfaceClasses) {
			if (classes.contains(interfaceClass) && (!assignablesInOrder.contains(interfaceClass))) {
				assignablesInOrder.add(interfaceClass);
			}
			findAssignablesInOrderFromInterface(interfaceClass.getInterfaces(), assignablesInOrder);
		}
	}
}
