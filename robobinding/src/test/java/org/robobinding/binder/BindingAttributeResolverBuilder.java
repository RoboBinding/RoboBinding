package org.robobinding.binder;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMapBuilder;

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
	ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider = new ViewAttributeBinderFactoryProvider(
		new PropertyAttributeParser(),
		new GroupAttributesResolver(),
		BinderFactoryBuilder.defaultViewListenersMapBuilder().build());
	ByBindingAttributeMappingsResolverFinder resolverFinder = new ByBindingAttributeMappingsResolverFinder(
		providersResolver, viewAttributeBinderFactoryProvider);
	return new BindingAttributeResolver(resolverFinder);
    }

}
