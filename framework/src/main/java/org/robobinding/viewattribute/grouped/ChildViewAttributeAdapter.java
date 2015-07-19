package org.robobinding.viewattribute.grouped;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.property.AbstractBindingProperty;
import org.robobinding.viewattribute.property.OneWayBindingProperty;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributeAdapter implements ChildViewAttribute {
	private PropertyViewAttributeBinder viewAttributeBinder;

	public ChildViewAttributeAdapter(Object view, OneWayPropertyViewAttribute<?, ?> viewAttribute, ValueModelAttribute attribute) {
		AbstractBindingProperty bindingProperty = new OneWayBindingProperty(view, viewAttribute, attribute);
		viewAttributeBinder = new PropertyViewAttributeBinder(bindingProperty, attribute.getName());
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		viewAttributeBinder.bindTo(bindingContext);
	}
}
