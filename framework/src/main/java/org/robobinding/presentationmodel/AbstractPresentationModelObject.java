package org.robobinding.presentationmodel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.robobinding.function.Bug;
import org.robobinding.function.FunctionSupply;
import org.robobinding.function.MethodDescription;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.PropertySupply;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractPresentationModelObject implements PropertySupply, FunctionSupply, ObservableBean {
	private final Class<?> presentationModelClass;
	protected final PresentationModelChangeSupport changeSupport;

	protected AbstractPresentationModelObject(Class<?> presentationModelClass, PresentationModelChangeSupport changeSupport) {
		this.presentationModelClass = presentationModelClass;
		this.changeSupport = changeSupport;
	}

	public abstract Set<String> propertyNames();

	public abstract Set<String> dataSetPropertyNames();
	/*
	protected Set<String> allPropertyNames() {
		Set<String> allPropertyNames = Sets.newHashSet(propertyNames());
		allPropertyNames.addAll(dataSetPropertyNames());
		return allPropertyNames;
	}*/

	public abstract Map<String, Set<String>> propertyDependencies();

	public abstract Set<Method> eventMethods();
	
	
	protected Method getMethod(String name) {
		return getMethod(name, new Class<?>[0]);
	}
	
	protected Method getMethod(String name, Class<?>... parameterTypes) {
		try {
			return presentationModelClass.getMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			MethodDescription description = createMethodDecription(name, parameterTypes);
			throw new Bug("No known method '"+description+"' generated", e);
		} catch (SecurityException e) {
			MethodDescription description = createMethodDecription(name, parameterTypes);
			throw new Bug("Incorrect method '"+description+"' generation", e);
		}
	}
	
	private MethodDescription createMethodDecription(String name, Class<?>... parameterTypes) {
		return  new MethodDescription(presentationModelClass, name, parameterTypes);
	}

	
	protected PropertyDescriptor createPropertyDescriptor(Class<?> propertyType, String propertyName, boolean readable, boolean writable) {
		return new PropertyDescriptor(presentationModelClass, propertyType, propertyName, readable, writable);
	}
	
	protected PropertyDescriptor createDataSetPropertyDescriptor(Class<?> propertyType, String propertyName) {
		return new PropertyDescriptor(presentationModelClass, propertyType, propertyName, true, false);
	}
	
	@Override
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	@Override
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public Class<?> getPresentationModelClass() {
		return presentationModelClass;
	}
}