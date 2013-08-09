/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.customview;

import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.ViewAttributeInitializer;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CustomBindingAttributeMapperAdapter<T extends View> implements BindingAttributeMappingsProvider<T> {
    private BindableView<T> bindableView;
    private PropertyAttributeParser propertyAttributeParser;

    public CustomBindingAttributeMapperAdapter(BindableView<T> bindableView, PropertyAttributeParser propertyAttributeParser) {
	this.bindableView = bindableView;
	this.propertyAttributeParser = propertyAttributeParser;
    }

    @Override
    public BindingAttributeMappingsImpl<T> createBindingAttributeMappings(ViewAttributeInitializer viewAttributeInitializer) {
	CustomBindingAttributeMappingsImpl<T> bindingAttributeMappings = new CustomBindingAttributeMappingsImpl<T>(
		viewAttributeInitializer, propertyAttributeParser);
	bindableView.mapBindingAttributes(bindingAttributeMappings);
	return bindingAttributeMappings;
    }

}
