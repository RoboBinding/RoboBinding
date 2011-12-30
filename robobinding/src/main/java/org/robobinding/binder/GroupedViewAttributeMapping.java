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
package org.robobinding.binder;

import java.util.Map;

import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.GroupedViewAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class GroupedViewAttributeMapping<T extends View> extends AbstractViewAttributeMapping<T>
{
	private final Class<? extends GroupedViewAttribute<T>> attributeClass;
	private final String[] attributeNames;
	
	public GroupedViewAttributeMapping(Class<? extends GroupedViewAttribute<T>> attributeClass, String[] attributeNames)
	{
		this.attributeClass = attributeClass;
		this.attributeNames = attributeNames;
	}

	@Override
	public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		if (bindingAttributeResolver.hasOneOfAttributes(attributeNames))
		{
			GroupedViewAttribute<T> groupedViewAttribute = createNewInstance(attributeClass);
			Map<String, String> attributes = Maps.newHashMap();
			
			for (String name : attributeNames)
			{
				if (bindingAttributeResolver.hasAttribute(name))
				{
					attributes.put(name, bindingAttributeResolver.findAttributeValue(name));
				}
			}
			
			groupedViewAttribute.setChildAttributes(attributes);
			
			bindingAttributeResolver.resolveAttributes(attributeNames, groupedViewAttribute);
		}
	}
}