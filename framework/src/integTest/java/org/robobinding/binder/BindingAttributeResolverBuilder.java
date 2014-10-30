package org.robobinding.binder;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewbinding.InitializedBindingAttributeMappingsProviders;
import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.viewbinding.ViewBinding;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeResolverBuilder {
	private ViewBindingMap bindingAttributeMappingsProviderMapBuilder;

	public BindingAttributeResolverBuilder() {
		bindingAttributeMappingsProviderMapBuilder = BinderFactoryBuilder.defaultViewBindingMap();
	}

	public <T extends View> BindingAttributeResolverBuilder mapView(Class<T> viewClass, ViewBinding<T> bindingAttributeMapper) {
		bindingAttributeMappingsProviderMapBuilder.put(viewClass, bindingAttributeMapper);
		return this;
	}

	public BindingAttributeResolver build() {
		InitializedBindingAttributeMappingsProviders providerMap = bindingAttributeMappingsProviderMapBuilder.buildInitializedBindingAttributeMappingsProviders();
		ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider = new ViewAttributeBinderFactoryProvider(new PropertyAttributeParser(),
				new GroupAttributesResolver(), BinderFactoryBuilder.defaultViewAddOnsBuilder().build());
		ByBindingAttributeMappingsResolverFinder resolverFinder = new ByBindingAttributeMappingsResolverFinder(providerMap, viewAttributeBinderFactoryProvider);
		return new BindingAttributeResolver(resolverFinder);
	}

}
