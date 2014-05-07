package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
abstract class AbstractProperty<T> implements PropertyValueModel<T> {
    private final ObservableBean observableBean;
    private final PropertyAccessor<T> propertyAccessor;

    protected AbstractProperty(ObservableBean observableBean, PropertyAccessor<T> propertyAccessor) {
	this.observableBean = observableBean;
	this.propertyAccessor = propertyAccessor;
    }

    @Override
    public T getValue() {
	return propertyAccessor.getValue(getBean());
    }

    @Override
    public void setValue(T newValue) {
	propertyAccessor.setValue(getBean(), newValue);
    }

    @Override
    public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	observableBean.addPropertyChangeListener(propertyAccessor.getPropertyName(), listener);
    }

    @Override
    public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	observableBean.removePropertyChangeListener(propertyAccessor.getPropertyName(), listener);
    }

    @Override
    public Class<?> getPropertyType() {
	return propertyAccessor.getPropertyType();
    }

    @Override
    public void checkReadWriteProperty(boolean isReadWriteProperty) {
	propertyAccessor.checkReadable();
	if (isReadWriteProperty) {
	    propertyAccessor.checkWritable();
	}
    }

    public PropertyAccessor<T> getPropertyAccessor() {
	return propertyAccessor;
    }

    protected Object getBean() {
	return observableBean.getBean();
    }

    @Override
    public String toString() {
	return propertyAccessor.toString();
    }
}
