package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class PropertyValueModelWrapper<T> extends PropertyWrapper implements PropertyValueModel<T> {
    private PropertyValueModel<T> propertyValueModel;

    public PropertyValueModelWrapper(PropertyValueModel<T> propertyValueModel) {
	super(propertyValueModel);
	this.propertyValueModel = propertyValueModel;
    }

    @Override
    public T getValue() {
	return propertyValueModel.getValue();
    }

    @Override
    public void setValue(T newValue) {
	propertyValueModel.setValue(newValue);
    }

    @Override
    public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	propertyValueModel.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	propertyValueModel.removePropertyChangeListener(listener);
    }
}
