package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOns;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractTwoWayPropertyViewAttributeBinderFactory {
	private final ViewAddOns viewAddOns;

	public AbstractTwoWayPropertyViewAttributeBinderFactory(ViewAddOns viewAddOns) {
		this.viewAddOns = viewAddOns;
	}

	protected PropertyViewAttributeBinder create(Object view, TwoWayPropertyViewAttribute<?, ?, ?> viewAttribute, 
			ValueModelAttribute attribute) {
		AbstractBindingProperty bindingProperty;
		ViewAddOn viewAddOn = viewAddOns.getMostSuitable(view);
		if (attribute.isTwoWayBinding()) {
			bindingProperty = new TwoWayBindingProperty(
					view, viewAddOn, 
					viewAttribute, attribute);
		} else {
			bindingProperty = new OneWayBindingProperty(view, 
					new OneWayPropertyViewAttributeAdapter(viewAttribute, viewAddOn), 
					attribute);
		}
		
		return new PropertyViewAttributeBinder(bindingProperty, attribute.getName());
	}
	
	private static class OneWayPropertyViewAttributeAdapter implements OneWayPropertyViewAttribute<Object, Object> {
		private final TwoWayPropertyViewAttribute<Object, ViewAddOn, Object> viewAttribute;
		private final ViewAddOn viewAddOn;
		
		@SuppressWarnings("unchecked")
		public OneWayPropertyViewAttributeAdapter(TwoWayPropertyViewAttribute<?, ?, ?> viewAttribute, ViewAddOn viewAddOn) {
			this.viewAttribute = (TwoWayPropertyViewAttribute<Object, ViewAddOn, Object>)viewAttribute;
			this.viewAddOn = viewAddOn;
		}
		
		@Override
		public void updateView(Object view, Object newValue) {
			viewAttribute.updateView(view, newValue, viewAddOn);
		}
	}
}
