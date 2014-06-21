package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.ViewListenersInjector;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeBinderProviderAdapter<T extends View> implements PropertyViewAttributeBinderProvider<T> {
    private final T view;
    private final MultiTypePropertyViewAttribute<T> multiTypeViewAttribute;
    private final ValueModelAttribute attribute;
    private final PropertyViewAttributeBinderFactory<T> propertyViewAttributeBinderFactory;
    private final ViewListenersInjector viewListenersInjector;

    public PropertyViewAttributeBinderProviderAdapter(
	    T view,
	    MultiTypePropertyViewAttribute<T> multiTypeViewAttribute,
	    ValueModelAttribute attribute,
	    PropertyViewAttributeBinderFactory<T> propertyViewAttributeBinderFactory,
	    ViewListenersInjector viewListenersInjector) {
	this.view = view;
	this.multiTypeViewAttribute = multiTypeViewAttribute;
	this.attribute = attribute;
	this.propertyViewAttributeBinderFactory = propertyViewAttributeBinderFactory;
	this.viewListenersInjector = viewListenersInjector;
    }

    @Override
    public PropertyViewAttributeBinder<T, ?> create(Class<?> propertyType) {
	PropertyViewAttribute<T, ?> viewAttribute = multiTypeViewAttribute.create(view, propertyType);
	if (viewAttribute != null) {
	    viewListenersInjector.injectIfRequired(viewAttribute, view);
	    return propertyViewAttributeBinderFactory.create(viewAttribute, attribute);
	} else {
	    return null;
	}
    }
}