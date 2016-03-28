package org.robobinding.viewattribute.property;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.ValueModel;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TwoWayBindingProperty extends AbstractBindingProperty {
	private final ViewAddOn viewAddOn;
	private final TwoWayPropertyViewAttribute<Object, ViewAddOn, Object> viewAttribute;
	
	private final UpdatePropagationLatch viewUpdatePropagationLatch;
	private final UpdatePropagationLatch modelUpdatePropagationLatch;

	@SuppressWarnings("unchecked")
	public TwoWayBindingProperty(Object view, ViewAddOn viewAddOn,
			TwoWayPropertyViewAttribute<?, ?, ?> viewAttribute, 
			ValueModelAttribute attribute) {
		super(view, attribute, isAlwaysPreInitializingView(viewAttribute));
		
		this.viewAddOn = viewAddOn;
		this.viewAttribute = (TwoWayPropertyViewAttribute<Object, ViewAddOn, Object>)viewAttribute;
		
		this.viewUpdatePropagationLatch = new UpdatePropagationLatch();
		this.modelUpdatePropagationLatch = new UpdatePropagationLatch();
	}

	@Override
	public void performBind(BindingContext context) {
		ValueModel<Object> valueModel = getPropertyValueModel(context);
		valueModel = new PropertyValueModelWrapper(valueModel);
		observeChangesOnTheValueModel(valueModel);
		viewAttribute.observeChangesOnTheView(viewAddOn, valueModel, view);
	}

	private void observeChangesOnTheValueModel(final ValueModel<Object> valueModel) {
		PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
			@Override
			public void propertyChanged() {
				modelUpdatePropagationLatch.turnOn();
				try {
					if (viewUpdatePropagationLatch.tryToPass())
						updateView(valueModel);
				} finally {
					modelUpdatePropagationLatch.turnOff();
				}

			}
		};
		PropertyChangeListenerInUiThread inUiThread = new PropertyChangeListenerInUiThread(propertyChangeListener);
		valueModel.addPropertyChangeListener(inUiThread);
	}

	@Override
	protected void updateView(ValueModel<Object> valueModel) {
		viewAttribute.updateView(view, valueModel.getValue(), viewAddOn);
	}
	
	@Override
	public ValueModel<Object> getPropertyValueModel(BindingContext context) {
		return context.getPropertyValueModel(attribute.getPropertyName());
	}

	private class PropertyValueModelWrapper implements ValueModel<Object> {
		private ValueModel<Object> propertyValueModel;

		public PropertyValueModelWrapper(ValueModel<Object> propertyValueModel) {
			this.propertyValueModel = propertyValueModel;
		}

		@Override
		public Object getValue() {
			return propertyValueModel.getValue();
		}

		@Override
		public void setValue(Object newValue) {
			viewUpdatePropagationLatch.turnOn();
			try {
				if (modelUpdatePropagationLatch.tryToPass())
					propertyValueModel.setValue(newValue);
			} finally {
				viewUpdatePropagationLatch.turnOff();
			}
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
}