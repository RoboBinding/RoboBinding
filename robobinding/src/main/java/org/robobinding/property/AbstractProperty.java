package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
abstract class AbstractProperty implements PropertyValueModel {
    private final ObservableBean observableBean;
    private final PropertyAccessor propertyAccessor;

    protected AbstractProperty(ObservableBean observableBean, PropertyAccessor propertyAccessor) {
	this.observableBean = observableBean;
	this.propertyAccessor = propertyAccessor;
    }

    @Override
    public Object getValue() {
	return propertyAccessor.getValue();
    }

    @Override
    public void setValue(Object newValue) {
	propertyAccessor.setValue(newValue);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
	observableBean.addPropertyChangeListener(propertyAccessor.getPropertyName(), listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
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

    @Override
    public String toString() {
	return propertyAccessor.getDescription();
    }
    
    public String decriptionWithExtraInformation(String extraInformation) {
	return propertyAccessor.decriptionWithExtraInformation(extraInformation);
    }
}
