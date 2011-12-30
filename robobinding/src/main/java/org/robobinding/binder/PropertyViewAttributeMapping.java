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

import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class PropertyViewAttributeMapping<T extends View> extends AbstractViewAttributeMapping<T>
{
	private String attributeName;
	private Class<? extends PropertyViewAttribute<T>> viewAttributeClass;
	
	public PropertyViewAttributeMapping(String attributeName, Class<? extends PropertyViewAttribute<T>> viewAttributeClass)
	{
		this.attributeName = attributeName;
		this.viewAttributeClass = viewAttributeClass;
	}

	public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		if (bindingAttributeResolver.hasAttribute(attributeName))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(attributeName);
			
			PropertyViewAttribute<T> propertyViewAttribute = createNewInstance(viewAttributeClass);
			propertyViewAttribute.setView(view);
			propertyViewAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(attributeValue));
			propertyViewAttribute.setPreInitializeViews(preInitializeViews);
			
			bindingAttributeResolver.resolveAttribute(attributeValue, propertyViewAttribute);
		}
	}
}
