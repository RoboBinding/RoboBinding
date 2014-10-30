package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOns;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TwoWayPropertyViewAttributeBinderFactory extends AbstractPropertyViewAttributeBinderFactory {
	private final TwoWayPropertyViewAttributeFactory<?> factory;
	private final ViewAddOns viewAddOns;
	
	public TwoWayPropertyViewAttributeBinderFactory(TwoWayPropertyViewAttributeFactory<?> factory, ViewAddOns viewAddOns, 
			PropertyAttributeParser propertyAttributeParser) {
		super(propertyAttributeParser);
		
		this.factory = factory;
		this.viewAddOns = viewAddOns;
	}
	
	@Override
	public PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		TwoWayPropertyViewAttribute<?, ?, ?> viewAttribute = factory.create();
		ViewAddOn viewAddOn = viewAddOns.getMostSuitable(view);
		AbstractBindingProperty bindingProperty = new TwoWayBindingProperty(view, viewAddOn, viewAttribute, attribute);
		return new PropertyViewAttributeBinder(bindingProperty, attribute.getName());
	}
}
