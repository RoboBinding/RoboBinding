package org.robobinding.binder;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;
import org.robobinding.viewattribute.impl.ViewAttributeInitializerFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeResolverBuilder {
    private BindingAttributeMappingsProviderMapBuilder bindingAttributeMappingsProviderMapBuilder;

    public BindingAttributeResolverBuilder() {
	bindingAttributeMappingsProviderMapBuilder = BinderFactoryBuilder.defaultBindingAttributeMappingsProviderMapBuilder();
    }

    public <T extends View> BindingAttributeResolverBuilder mapView(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper) {
	bindingAttributeMappingsProviderMapBuilder.put(viewClass, bindingAttributeMapper);
	return this;
    }

    public BindingAttributeResolver build() {
	BindingAttributeMappingsProviderResolver providersResolver = new BindingAttributeMappingsProviderResolver(
		bindingAttributeMappingsProviderMapBuilder.build());
	ViewAttributeInitializerFactory viewAttributeInitializerFactory = new ViewAttributeInitializerFactory(
		BinderFactoryBuilder.defaultViewListenersMapBuilder().build());
	ByBindingAttributeMappingsResolverFinder resolverFinder = new ByBindingAttributeMappingsResolverFinder(
		providersResolver, viewAttributeInitializerFactory);
	return new BindingAttributeResolver(resolverFinder);
    }

}
