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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewAttributeMappings;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class ViewAttributeMappingsImpl<T extends View> implements ViewAttributeMappings<T>
{
	private Map<String, Class<? extends PropertyViewAttribute<? extends View>>> propertyViewAttributeMappings;
	private Map<String, Class<? extends AbstractCommandViewAttribute<? extends View>>> commandViewAttributeMappings;
	private Map<GroupedAttributeDetailsImpl, Class<? extends AbstractGroupedViewAttribute<? extends View>>> groupedViewAttributeMappings;

	public ViewAttributeMappingsImpl()
	{
		propertyViewAttributeMappings = Maps.newHashMap();
		commandViewAttributeMappings = Maps.newHashMap();
		groupedViewAttributeMappings = Maps.newHashMap();
	}

	@Override
	public void mapProperty(Class<? extends PropertyViewAttribute<T>> propertyViewAttributeClass, String attributeName)
	{
		Validate.notBlank(attributeName, "attribute name must not be empty");
		Validate.notNull(propertyViewAttributeClass, "propertyViewAttributeClass must not be null");
		propertyViewAttributeMappings.put(attributeName, propertyViewAttributeClass);
	}

	@Override
	public void mapCommand(Class<? extends AbstractCommandViewAttribute<T>> commandViewAttributeClass, String attributeName)
	{
		Validate.notBlank(attributeName, "attribute name must not be empty");
		Validate.notNull(commandViewAttributeClass, "commandViewAttributeClass must not be null");
		commandViewAttributeMappings.put(attributeName, commandViewAttributeClass);
	}

	@Override
	public void mapGroup(Class<? extends AbstractGroupedViewAttribute<T>> groupedViewAttribute,	String... attributeNames)
	{
		Validate.notNull(attributeNames, "attribute names must not be null");
		Validate.notNull(groupedViewAttribute, "groupedPropertyViewAttribute must not be null");
		GroupedAttributeDetailsImpl groupedPropertyAttribute = new GroupedAttributeDetailsImpl(attributeNames);
		groupedViewAttributeMappings.put(groupedPropertyAttribute, groupedViewAttribute);
	}

	public Collection<String> getPropertyAttributes()
	{
		return propertyViewAttributeMappings.keySet();
	}

	public PropertyViewAttribute<View> createPropertyViewAttribute(String propertyAttribute)
	{
		Class<? extends PropertyViewAttribute<? extends View>> propertyViewAttributeClass = propertyViewAttributeMappings.get(propertyAttribute);
		@SuppressWarnings("unchecked")
		PropertyViewAttribute<View> propertyViewAttribute = (PropertyViewAttribute<View>) newViewAttribute(propertyViewAttributeClass);
		return propertyViewAttribute;
	}

	public Collection<String> getCommandAttributes()
	{
		return commandViewAttributeMappings.keySet();
	}

	public AbstractCommandViewAttribute<View> createCommandViewAttribute(String commandAttribute)
	{
		Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass = commandViewAttributeMappings.get(commandAttribute);
		@SuppressWarnings("unchecked")
		AbstractCommandViewAttribute<View> commandViewAttribute = (AbstractCommandViewAttribute<View>) newViewAttribute(commandViewAttributeClass);
		return commandViewAttribute;
	}

	public Collection<GroupedAttributeDetailsImpl> getGroupedPropertyAttributes()
	{
		return groupedViewAttributeMappings.keySet();
	}

	public AbstractGroupedViewAttribute<View> createGroupedViewAttribute(GroupedAttributeDetailsImpl groupedAttributeDetails)
	{
		Class<? extends AbstractGroupedViewAttribute<? extends View>> groupedViewAttributeClass = groupedViewAttributeMappings.get(groupedAttributeDetails);
		@SuppressWarnings("unchecked")
		AbstractGroupedViewAttribute<View> groupedPropertyViewAttribute = (AbstractGroupedViewAttribute<View>)newViewAttribute(groupedViewAttributeClass);
		return groupedPropertyViewAttribute;
	}
	
	private ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass)
	{
		try
		{
			return viewAttributeClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " does not have an empty default constructor");
		} 
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
		}
	}

}
