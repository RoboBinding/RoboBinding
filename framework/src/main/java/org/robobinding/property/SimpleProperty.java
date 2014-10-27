package org.robobinding.property;

import java.util.Set;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SimpleProperty implements PropertyValueModel {
	private final ObservableBean bean;
	private final PropertyDescriptor descriptor;
	private final AbstractGetSet<Object> getSet;
	@SuppressWarnings("unchecked")
	public SimpleProperty(ObservableBean bean, PropertyDescriptor descriptor, AbstractGetSet<?> getSet) {
		this.bean = bean;
		this.descriptor = descriptor;
		this.getSet = (AbstractGetSet<Object>)getSet;
	}
	
	@Override
	public Class<?> getPropertyType() {
		return descriptor.getPropertyType();
	}

	@Override
	public void checkReadWriteProperty(boolean isReadWriteProperty) {
		descriptor.checkReadWriteProperty(isReadWriteProperty);
	}

	@Override
	public Object getValue() {
		return getSet.getValue();
	}

	@Override
	public void setValue(Object newValue) {
		getSet.setValue(newValue);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		bean.addPropertyChangeListener(descriptor.getName(), listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		bean.removePropertyChangeListener(descriptor.getName(), listener);
	}
	
	@Override
	public String toString() {
		return descriptor.getDescription();
	}

	public String decriptionWithDependencies(Set<String> dependentProperties) {
		return descriptor.decriptionWithDependencies(dependentProperties);
	}

}
