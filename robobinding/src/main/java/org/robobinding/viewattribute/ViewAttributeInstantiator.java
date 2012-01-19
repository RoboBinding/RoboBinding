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
public class ViewAttributeInstantiator<T extends View>
{
	private T view;
	private boolean preInitializeViews;
	private GroupedAttributeDetails groupedAttributeDetails;

	ViewAttributeInstantiator(T view, boolean preInitializeViews, GroupedAttributeDetails groupedAttributeDetails)
	{
		this.view = view;
		this.preInitializeViews = preInitializeViews;
		this.groupedAttributeDetails = groupedAttributeDetails;
	}

	public <PropertyViewAttributeType extends PropertyViewAttribute<? super T>>  PropertyViewAttributeType newPropertyViewAttribute(
			Class<PropertyViewAttributeType> propertyViewAttributeClass, String propertyAttribute)
	{
		@SuppressWarnings("unchecked")
		PropertyViewAttributeType propertyViewAttribute = (PropertyViewAttributeType)newViewAttribute(propertyViewAttributeClass);
		propertyViewAttribute.setView(view);
		propertyViewAttribute.setAttributeValue(groupedAttributeDetails.attributeValueFor(propertyAttribute));
		propertyViewAttribute.setPreInitializeView(preInitializeViews);
		return propertyViewAttribute;
	}

	public <CommandViewAttributeType extends AbstractCommandViewAttribute<? super T>> CommandViewAttributeType newCommandViewAttribute(
			Class<CommandViewAttributeType> commandViewAttributeClass, String commandAttribute)
	{
		@SuppressWarnings("unchecked")
		CommandViewAttributeType commandViewAttribute = (CommandViewAttributeType)newViewAttribute(commandViewAttributeClass);
		commandViewAttribute.setView(view);
		commandViewAttribute.setCommandName(groupedAttributeDetails.attributeValueFor(commandAttribute));
		return commandViewAttribute;
	}

	private ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass)
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
}
