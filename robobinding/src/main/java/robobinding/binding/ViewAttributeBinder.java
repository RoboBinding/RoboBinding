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
package robobinding.binding;

import java.util.List;
import java.util.Map;

import robobinding.beans.PresentationModelAdapter;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
class ViewAttributeBinder
{
	static final String ROBOBINDING_NAMESPACE = "http://robobinding.org/robobinding.android";

	private View view;
	private Map<String, String> bindingAttributes = Maps.newHashMap();
	private List<ViewAttributeProvider> providers;
	
	ViewAttributeBinder(View view, AttributeSet attrs)
	{
		this.view = view;
		initializeFromAttributeSet(attrs);
	}
	
	private void initializeFromAttributeSet(AttributeSet attributeSet)
	{
		for (int i = 0; i < attributeSet.getAttributeCount(); i++)
		{
			String attributeName = attributeSet.getAttributeName(i);
			String attributeValue = attributeSet.getAttributeValue(ROBOBINDING_NAMESPACE, attributeName);
			
			if (attributeValue != null)
				bindingAttributes.put(attributeName, attributeValue);
		}
	}

	public void bind(PresentationModelAdapter presentationModelAdapter)
	{
		initializeProviders();
		
		for (String attributeName : bindingAttributes.keySet())
		{
			String attributeValue = bindingAttributes.get(attributeName);
			
			if (isCommand(attributeName))
			{
				CommandViewAttribute commandViewAttribute = createCommandViewAttribute(attributeName);
				commandViewAttribute.bind(presentationModelAdapter, attributeValue);
			}
			else
			{
				Class<?> propertyType = presentationModelAdapter.getPropertyType(attributeValue);
				PropertyViewAttribute propertyViewAttribute = createPropertyViewAttribute(attributeName, propertyType);
				propertyViewAttribute.bind(presentationModelAdapter, attributeValue);
			}
		}
	}

	private void initializeProviders()
	{
		providers = Lists.newArrayList();
	}

	private PropertyViewAttribute createPropertyViewAttribute(String attributeName, Class<?> propertyType)
	{
		for (ViewAttributeProvider viewAttributeProvider : providers)
		{
			PropertyViewAttribute propertyViewAttribute = viewAttributeProvider.createPropertyViewAttribute(view, propertyType, attributeName);
		
			if (propertyViewAttribute != null)
				return propertyViewAttribute;
		}
		
		throw new RuntimeException("No PropertyViewAttribute implemented for binding attribute: " + attributeName + " and value model type: " + propertyType);
	}

	private CommandViewAttribute createCommandViewAttribute(String attributeName)
	{
		for (ViewAttributeProvider viewAttributeProvider : providers)
		{
			CommandViewAttribute commandViewAttribute = viewAttributeProvider.createCommandViewAttribute(view, attributeName);
		
			if (commandViewAttribute != null)
				return commandViewAttribute;
		}
		
		throw new RuntimeException("No CommandViewAttribute implemented for binding attribute: " + attributeName);
	}

	private boolean isCommand(String attributeName)
	{
		for (ViewAttributeProvider viewAttributeProvider : providers)
		{
			if (viewAttributeProvider.isCommand(attributeName))
				return true;
		}
		
		return false;
	}

	Map<String, String> getBindingAttributes()
	{
		return bindingAttributes;
	}

	View getView()
	{
		return view;
	}
}
