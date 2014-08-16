package org.robobinding.viewattribute.impl;

import java.util.Map;

import org.robobinding.internal.guava.Maps;
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
	mappings = Maps.newHashMap();
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
