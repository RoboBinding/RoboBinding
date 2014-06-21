package org.robobinding.binder;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewattribute.impl.ViewListenersProvider;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;
import org.robobinding.widget.view.ViewListenersMap;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeBinderFactoryProvider {
    private final ViewListenersMap viewListenersMap;
    private final PropertyAttributeParser propertyAttributeParser;
    private final GroupAttributesResolver resolvedGroupAttributesFactory;

    public ViewAttributeBinderFactoryProvider(PropertyAttributeParser propertyAttributeParser,
            GroupAttributesResolver resolvedGroupAttributesFactory,
            ViewListenersMap viewListenersMap) {
        this.propertyAttributeParser = propertyAttributeParser;
        this.resolvedGroupAttributesFactory = resolvedGroupAttributesFactory;
        this.viewListenersMap = viewListenersMap;
    }

    public <T extends View> ViewAttributeBinderFactory<T> create(T view) {
	ViewListenersInjector viewListenersInjector = new ViewListenersProvider(viewListenersMap);
	return new ViewAttributeBinderFactory<T>(
		view,
		new PropertyViewAttributeBinderFactory<T>(view),
		propertyAttributeParser,
		resolvedGroupAttributesFactory,
		viewListenersInjector);
    }
}
