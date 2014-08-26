package org.robobinding.viewattribute.property;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.ViewListenersInjector;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyViewAttributeBinderProviderAdapter<ViewType> implements PropertyViewAttributeBinderProvider<ViewType> {
    private final ViewType view;
    private final MultiTypePropertyViewAttribute<ViewType> multiTypeViewAttribute;
    private final ValueModelAttribute attribute;
    private final PropertyViewAttributeBinderFactory<ViewType> propertyViewAttributeBinderFactory;
    private final ViewListenersInjector viewListenersInjector;

    public PropertyViewAttributeBinderProviderAdapter(
	    ViewType view,
	    MultiTypePropertyViewAttribute<ViewType> multiTypeViewAttribute,
	    ValueModelAttribute attribute,
	    PropertyViewAttributeBinderFactory<ViewType> propertyViewAttributeBinderFactory,
	    ViewListenersInjector viewListenersInjector) {
	this.view = view;
	this.multiTypeViewAttribute = multiTypeViewAttribute;
	this.attribute = attribute;
	this.propertyViewAttributeBinderFactory = propertyViewAttributeBinderFactory;
	this.viewListenersInjector = viewListenersInjector;
    }

    @Override
    public PropertyViewAttributeBinder<ViewType, ?> create(Class<?> propertyType) {
	PropertyViewAttribute<ViewType, ?> viewAttribute = multiTypeViewAttribute.create(view, propertyType);
	if (viewAttribute != null) {
	    viewListenersInjector.injectIfRequired(viewAttribute, view);
	    return propertyViewAttributeBinderFactory.create(viewAttribute, attribute);
	} else {
	    return null;
	}
    }
}