package org.robobinding.presentationmodel;

import java.util.Map;
import java.util.Set;

import org.robobinding.function.FunctionSupply;
import org.robobinding.function.MethodDescriptor;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyDescriptor;
import org.robobinding.property.PropertySupply;
import org.robobinding.property.PropertyUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractPresentationModelObject implements PropertySupply, FunctionSupply, ObservableBean {
	private final Class<?> presentationModelClass;
	protected final PresentationModelChangeSupport changeSupport;

	protected AbstractPresentationModelObject(Object presentationModel) {
		this.presentationModelClass = presentationModel.getClass();
		if(presentationModel instanceof HasPresentationModelChangeSupport) {
			this.changeSupport = ((HasPresentationModelChangeSupport)presentationModel).getPresentationModelChangeSupport();
		} else {
			this.changeSupport = new PresentationModelChangeSupport(presentationModelClass, 
					PropertyUtils.getPropertyNames(presentationModelClass));
		}
	}
	
	/*
	private Set<String> allPropertyNames() {
		Set<String> all = Sets.newHashSet();
		all.addAll(propertyNames());
		all.addAll(dataSetPropertyNames());
		return all;
	}*/

	public abstract Set<String> propertyNames();

	public abstract Set<String> dataSetPropertyNames();
	/*
	protected Set<String> allPropertyNames() {
		Set<String> allPropertyNames = Sets.newHashSet(propertyNames());
		allPropertyNames.addAll(dataSetPropertyNames());
		return allPropertyNames;
	}*/

	public abstract Map<String, Set<String>> propertyDependencies();

	public abstract Set<MethodDescriptor> eventMethods();
	
	
	protected MethodDescriptor createMethodDescriptor(String name) {
		return new MethodDescriptor(name, new Class[0]);
	}
	
	protected MethodDescriptor createMethodDescriptor(String name, Class<?>... parameterTypes) {
		return new MethodDescriptor(name, parameterTypes);
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