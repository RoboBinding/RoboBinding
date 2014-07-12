package org.robobinding.viewattribute.property;

import org.robobinding.AttributeResolutionException;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeBinderFactory<T extends View> {
    private final T view;

    public PropertyViewAttributeBinderFactory(T view) {
	this.view = view;
    }

    public PropertyViewAttributeBinder<T, ?> create(PropertyViewAttribute<T, ?> viewAttribute,
	    ValueModelAttribute attribute) {
	AbstractBindingProperty<T, Object> bindingProperty = determineBindingProperty(viewAttribute, attribute);

	return new PropertyViewAttributeBinder<T, Object>(
		bindingProperty,
		isAlwaysPreInitializingView(viewAttribute));
    }

    @SuppressWarnings("unchecked")
    private AbstractBindingProperty<T, Object> determineBindingProperty(PropertyViewAttribute<T, ?> viewAttribute, ValueModelAttribute attribute) {
	if (attribute.isTwoWayBinding()) {
	    if (!(viewAttribute instanceof TwoWayPropertyViewAttribute)) {
		throw new AttributeResolutionException(attribute.getName(), "the attribute doesn't support two-way binding");
	    }
	    return new TwoWayBindingProperty<T, Object>(view, (TwoWayPropertyViewAttribute<T, Object>) viewAttribute, attribute);
	} else {
	    return new OneWayBindingProperty<T, Object>(view, (PropertyViewAttribute<T, Object>) viewAttribute, attribute);
	}
    }

    private boolean isAlwaysPreInitializingView(PropertyViewAttribute<T, ?> viewAttribute) {
	return viewAttribute instanceof AlwaysPreInitializingView;
    }
}
