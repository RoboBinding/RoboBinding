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
package org.robobinding.viewattribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeInstantiator
{
	public PropertyViewAttribute<View> newPropertyViewAttribute(Class<? extends PropertyViewAttribute<? extends View>> propertyViewAttributeClass, View view,
			String attributeValue, boolean preInitializeViews)
	{
		@SuppressWarnings("unchecked")
		PropertyViewAttribute<View> propertyViewAttribute = (PropertyViewAttribute<View>) newViewAttribute(propertyViewAttributeClass);
		injectPropertyAttributeValues(propertyViewAttribute, view, attributeValue, preInitializeViews);
		return propertyViewAttribute;
	}

	public AbstractCommandViewAttribute<View> newCommandViewAttribute(Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass,
			View view, String attributeValue)
	{
		@SuppressWarnings("unchecked")
		AbstractCommandViewAttribute<View> commandViewAttribute = (AbstractCommandViewAttribute<View>) newViewAttribute(commandViewAttributeClass);
		injectCommandAttributeValues(commandViewAttribute, view, attributeValue);
		return commandViewAttribute;
	}

	public AbstractGroupedViewAttribute<View> newGroupedViewAttribute(Class<? extends AbstractGroupedViewAttribute<? extends View>> groupedViewAttributeClass,
			View view, boolean preInitializeViews, GroupedAttributeDetails groupedAttributeDetails)
	{
		@SuppressWarnings("unchecked")
		AbstractGroupedViewAttribute<View> groupedViewAttribute = (AbstractGroupedViewAttribute<View>) newViewAttribute(groupedViewAttributeClass);
		injectGroupedAttributeValues(groupedViewAttribute, view, preInitializeViews, groupedAttributeDetails);
		return groupedViewAttribute;
	}

	ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass)
	{
		try
		{
			return viewAttributeClass.newInstance();
		} catch (InstantiationException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " does not have an empty default constructor");
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
		}
	}

	void injectPropertyAttributeValues(PropertyViewAttribute<View> propertyViewAttribute, View view, String attributeValue, boolean preInitializeViews)
	{
		propertyViewAttribute.setView(view);
		propertyViewAttribute.setAttributeValue(attributeValue);
		propertyViewAttribute.setPreInitializeView(preInitializeViews);
	}

	<T extends View> void injectCommandAttributeValues(AbstractCommandViewAttribute<T> commandViewAttribute, T view, String commandName)
	{
		commandViewAttribute.setView(view);
		commandViewAttribute.setCommandName(commandName);
	}

	void injectGroupedAttributeValues(AbstractGroupedViewAttribute<View> groupedViewAttribute, View view, boolean preInitializeViews,
			GroupedAttributeDetails groupedAttributeDetails)
	{
		groupedViewAttribute.setView(view);
		groupedViewAttribute.setPreInitializeViews(preInitializeViews);
		groupedViewAttribute.setGroupedAttributeDetails(groupedAttributeDetails);
	}
}
