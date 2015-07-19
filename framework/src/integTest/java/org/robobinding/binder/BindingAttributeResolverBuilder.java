package org.robobinding.binder;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.grouped.GroupAttributesResolver;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactories;
import org.robobinding.viewbinding.ViewBinding;
import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.widgetaddon.ViewAddOnInjector;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeResolverBuilder {
	private ViewBindingMap viewBindingMap;

	public BindingAttributeResolverBuilder() {
		viewBindingMap = BinderFactoryBuilder.defaultViewBindingMap();
	}

	public <T extends View> BindingAttributeResolverBuilder mapView(Class<T> viewClass, ViewBinding<T> bindingAttributeMapper) {
		viewBindingMap.put(viewClass, bindingAttributeMapper);
		return this;
	}

	public BindingAttributeResolver build() {
		ViewAttributeBinderFactories viewAttributeBinderFactories = new ViewAttributeBinderFactories(new PropertyAttributeParser(),
				new GroupAttributesResolver(), new ViewAddOnInjector(BinderFactoryBuilder.defaultViewAddOnsBuilder().build()));
		ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
				viewBindingMap.buildInitializedBindingAttributeMappingsProviders(), viewAttributeBinderFactories);
		return new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	}

}
