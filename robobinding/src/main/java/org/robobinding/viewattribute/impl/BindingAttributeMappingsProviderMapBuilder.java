package org.robobinding.viewattribute.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappingsProvider;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeMappingsProviderMapBuilder {
    private final Map<Class<? extends View>, BindingAttributeMappingsProvider<? extends View>> mappings;
    private final PropertyAttributeParser propertyAttributeParser;
    
    public BindingAttributeMappingsProviderMapBuilder(PropertyAttributeParser propertyAttributeParser) {
	mappings = newHashMap();
	this.propertyAttributeParser = propertyAttributeParser;
    }
    
    public <T extends View> BindingAttributeMappingsProviderMapBuilder put(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper) {
	mappings.put(viewClass, new BindingAttributeMapperAdapter<T>(bindingAttributeMapper, propertyAttributeParser));
	return this;
    }
    
    public BindingAttributeMappingsProviderMap build() {
	return new BindingAttributeMappingsProviderMap(mappings);
    }
}
