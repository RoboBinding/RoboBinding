package org.robobinding.binder;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.ViewBinding;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;
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

	public <T extends View> BindingAttributeResolverBuilder mapView(Class<T> viewClass, ViewBinding<T> bindingAttributeMapper) {
		bindingAttributeMappingsProviderMapBuilder.put(viewClass, bindingAttributeMapper);
		return this;
	}

	public BindingAttributeResolver build() {
		BindingAttributeMappingsProviderMap providerMap = bindingAttributeMappingsProviderMapBuilder.build();
		ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider = new ViewAttributeBinderFactoryProvider(new PropertyAttributeParser(),
				new GroupAttributesResolver(), BinderFactoryBuilder.defaultViewListenersMapBuilder().build());
		ByBindingAttributeMappingsResolverFinder resolverFinder = new ByBindingAttributeMappingsResolverFinder(providerMap, viewAttributeBinderFactoryProvider);
		return new BindingAttributeResolver(resolverFinder);
	}

}
