package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class PropertyValueModelWrapper extends PropertyWrapper implements PropertyValueModel {
	private final PropertyValueModel propertyValueModel;

	public PropertyValueModelWrapper(PropertyValueModel propertyValueModel) {
		super(propertyValueModel);
		this.propertyValueModel = propertyValueModel;
	}

	@Override
	public Object getValue() {
		return propertyValueModel.getValue();
	}

	@Override
	public void setValue(Object newValue) {
		propertyValueModel.setValue(newValue);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyValueModel.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyValueModel.removePropertyChangeListener(listener);
	}
}
