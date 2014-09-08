package org.robobinding.viewattribute.property;

import org.robobinding.AttributeResolutionException;
import org.robobinding.attribute.ValueModelAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeBinderFactory<ViewType> {
    private final ViewType view;

    public PropertyViewAttributeBinderFactory(ViewType view) {
	this.view = view;
    }

    public PropertyViewAttributeBinder<ViewType, ?> create(PropertyViewAttribute<ViewType, ?> viewAttribute,
	    ValueModelAttribute attribute) {
	AbstractBindingProperty<ViewType, Object> bindingProperty = determineBindingProperty(viewAttribute, attribute);

	return new PropertyViewAttributeBinder<ViewType, Object>(
		bindingProperty,
		isAlwaysPreInitializingView(viewAttribute));
    }

    @SuppressWarnings("unchecked")
    private AbstractBindingProperty<ViewType, Object> determineBindingProperty(
	    PropertyViewAttribute<ViewType, ?> viewAttribute, ValueModelAttribute attribute) {
	if (attribute.isTwoWayBinding()) {
	    if (!(viewAttribute instanceof TwoWayPropertyViewAttribute)) {
		throw new AttributeResolutionException(attribute.getName(), "the attribute doesn't support two-way binding");
	    }
	    return new TwoWayBindingProperty<ViewType, Object>(view, (TwoWayPropertyViewAttribute<ViewType, Object>) viewAttribute, attribute);
	} else {
	    return new OneWayBindingProperty<ViewType, Object>(view, (PropertyViewAttribute<ViewType, Object>) viewAttribute, attribute);
	}
    }

    private boolean isAlwaysPreInitializingView(PropertyViewAttribute<ViewType, ?> viewAttribute) {
	return viewAttribute instanceof AlwaysPreInitializingView;
    }
}
