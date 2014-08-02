package org.robobinding.viewattribute.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.robobinding.viewattribute.ViewBinding;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeMappingsProviderMapBuilder implements BindingAttributeMappingsProviderMappings {
    private final Map<Class<? extends View>, BindingAttributeMappingsProvider<? extends View>> mappings;

    public BindingAttributeMappingsProviderMapBuilder() {
	mappings = newHashMap();
    }

    public <T extends View> BindingAttributeMappingsProviderMapBuilder put(Class<T> viewClass, ViewBinding<T> bindingAttributeMapper) {
	mappings.put(viewClass, new ViewBindingAdapter<T>(bindingAttributeMapper));
	return this;
    }
    
    @Override
    public <T extends View> void put(Class<T> viewClass, BindingAttributeMappingsProvider<T> bindingAttributeMappingsProvider) {
	mappings.put(viewClass, bindingAttributeMappingsProvider);
    }

    public BindingAttributeMappingsProviderMap build() {
	return new BindingAttributeMappingsProviderMap(mappings);
    }
}
