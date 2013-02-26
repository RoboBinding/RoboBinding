/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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


import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeProvider;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingAttributeMapperAdapter<T extends View> implements BindingAttributeProvider<T>
{
	private BindingAttributeMapper<T> bindingAttributeMapper;
	public BindingAttributeMapperAdapter(BindingAttributeMapper<T> bindingAttributeMapper)
	{
		this.bindingAttributeMapper = bindingAttributeMapper;
	}
	
	public BindingAttributeMappingsImpl<T> createBindingAttributeMappings(ViewAttributeInitializer viewAttributeInitializer)
	{
		BindingAttributeMappingsImpl<T> bindingAttributeMappings = new BindingAttributeMappingsImpl<T>(viewAttributeInitializer);
		bindingAttributeMapper.mapBindingAttributes(bindingAttributeMappings);
		return bindingAttributeMappings;
	}
}
