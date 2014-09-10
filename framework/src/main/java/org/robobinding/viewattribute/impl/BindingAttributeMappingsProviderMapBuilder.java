package org.robobinding.viewattribute.impl;

import java.util.Map;

import org.robobinding.viewattribute.ViewBinding;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeMappingsProviderMapBuilder implements BindingAttributeMappingsProviderMappings {
    private final Map<Class<?>, BindingAttributeMappingsProvider<?>> mappings;

    public BindingAttributeMappingsProviderMapBuilder() {
	mappings = Maps.newHashMap();
    }

    public <ViewType> BindingAttributeMappingsProviderMapBuilder put(Class<ViewType> viewClass, ViewBinding<ViewType> bindingAttributeMapper) {
	mappings.put(viewClass, new ViewBindingAdapter<ViewType>(bindingAttributeMapper));
	return this;
    }
    
    @Override
    public <ViewType> void put(Class<ViewType> viewClass, BindingAttributeMappingsProvider<ViewType> bindingAttributeMappingsProvider) {
	mappings.put(viewClass, bindingAttributeMappingsProvider);
    }

    public BindingAttributeMappingsProviderMap build() {
	return new BindingAttributeMappingsProviderMap(mappings);
    }
}
