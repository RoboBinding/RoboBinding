package org.robobinding.viewattribute.property;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.widgetaddon.ViewAddOnInjector;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class OneWayPropertyViewAttributeBinderFactory extends AbstractPropertyViewAttributeBinderFactory {
	private final OneWayPropertyViewAttributeFactory<?> factory;
	private final ViewAddOnInjector viewAddOnInjector;
	
	public OneWayPropertyViewAttributeBinderFactory(OneWayPropertyViewAttributeFactory<?> factory, 
			ViewAddOnInjector viewAddOnInjector, PropertyAttributeParser propertyAttributeParser) {
		super(propertyAttributeParser);
		
		this.factory = factory;
		this.viewAddOnInjector = viewAddOnInjector;
	}
	
	@Override
	public PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
		OneWayPropertyViewAttribute<?, ?> viewAttribute = factory.create();
		viewAddOnInjector.injectIfRequired(viewAttribute, view);
		AbstractBindingProperty bindingProperty = new OneWayBindingProperty(view, viewAttribute, attribute);
		return new PropertyViewAttributeBinder(bindingProperty, attribute.getName());
	}
}
