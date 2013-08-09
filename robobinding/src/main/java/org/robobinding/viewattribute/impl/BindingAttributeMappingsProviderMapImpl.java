/**
 * Copyright 2013 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
public class BindingAttributeMappingsProviderMapImpl implements BindingAttributeMappingsProviderMap {
    private final Map<Class<? extends View>, BindingAttributeMappingsProvider<? extends View>> mappings;
    private final PropertyAttributeParser propertyAttributeParser;
    
    public BindingAttributeMappingsProviderMapImpl(PropertyAttributeParser propertyAttributeParser) {
	mappings = newHashMap();
	this.propertyAttributeParser = propertyAttributeParser;
    }
    
    private BindingAttributeMappingsProviderMapImpl(
	    Map<Class<? extends View>, BindingAttributeMappingsProvider<? extends View>> mappings, 
	    PropertyAttributeParser propertyAttributeParser) {
	this.mappings = newHashMap(mappings);
	this.propertyAttributeParser = propertyAttributeParser;
    }
    
    @Override
    public BindingAttributeMappingsProvider<? extends View> find(Class<? extends View> viewClass) {
        return mappings.get(viewClass);
    }
    
    public <T extends View> void put(Class<T> viewClass, BindingAttributeMapper<T> bindingAttributeMapper) {
	mappings.put(viewClass, new BindingAttributeMapperAdapter<T>(bindingAttributeMapper, propertyAttributeParser));
    }
    
    public BindingAttributeMappingsProviderMapImpl copy() {
	return new BindingAttributeMappingsProviderMapImpl(mappings, propertyAttributeParser);
    }
}
