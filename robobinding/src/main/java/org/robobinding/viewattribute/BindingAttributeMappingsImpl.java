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
package org.robobinding.viewattribute;

import java.util.Collection;
import java.util.Map;

import org.robobinding.binder.GroupedAttributeDetailsImpl;
import org.robobinding.internal.com_google_common.collect.Maps;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingAttributeMappingsImpl<T extends View> implements BindingAttributeMappings<T>
{
	private T view;
	private boolean preInitializeViews;
	
	private Map<String, Class<? extends PropertyViewAttribute<? extends View>>> propertyViewAttributeMappings;
	private Map<String, Class<? extends AbstractCommandViewAttribute<? extends View>>> commandViewAttributeMappings;
	private Map<GroupedAttributeDetailsImpl, Class<? extends AbstractGroupedViewAttribute<? extends View>>> groupedViewAttributeMappings;
	private ViewAttributeInstantiator viewAttributeInstantiator;
	
	public BindingAttributeMappingsImpl(T view, boolean preInitializeViews)
	{
		this.view = view;
		this.preInitializeViews = preInitializeViews;
		
		propertyViewAttributeMappings = Maps.newHashMap();
		commandViewAttributeMappings = Maps.newHashMap();
		groupedViewAttributeMappings = Maps.newHashMap();
		viewAttributeInstantiator = new ViewAttributeInstantiator();
	}

	@Override
	public void mapPropertyAttribute(Class<? extends PropertyViewAttribute<T>> propertyViewAttributeClass, String attributeName)
	{
		addPropertyViewAttributeMapping(propertyViewAttributeClass, attributeName);
	}

	@Override
	public void mapCommandAttribute(Class<? extends AbstractCommandViewAttribute<T>> commandViewAttributeClass, String attributeName)
	{
		addCommandViewAttributeMapping(commandViewAttributeClass, attributeName);
	}

	@Override
	public void mapGroupedAttribute(Class<? extends AbstractGroupedViewAttribute<T>> groupedViewAttributeClass,	String... attributeNames)
	{
		addGroupedViewAttributeMapping(groupedViewAttributeClass, attributeNames);
	}

	protected void addPropertyViewAttributeMapping(Class<? extends PropertyViewAttribute<?>> propertyViewAttributeClass, String attributeName)
	{
		propertyViewAttributeMappings.put(attributeName, propertyViewAttributeClass);
	}
	
	protected void addCommandViewAttributeMapping(Class<? extends AbstractCommandViewAttribute<?>> commandViewAttributeClass, String attributeName)
	{
		commandViewAttributeMappings.put(attributeName, commandViewAttributeClass);
	}
	
	protected void addGroupedViewAttributeMapping(Class<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeClass,	String... attributeNames)
	{
		GroupedAttributeDetailsImpl groupedPropertyAttribute = new GroupedAttributeDetailsImpl(attributeNames);
		groupedViewAttributeMappings.put(groupedPropertyAttribute, groupedViewAttributeClass);
	}
	
	public Collection<String> getPropertyAttributes()
	{
		return propertyViewAttributeMappings.keySet();
	}

	public PropertyViewAttribute<View> createPropertyViewAttribute(String propertyAttribute, String attributeValue)
	{
		Class<? extends PropertyViewAttribute<?>> propertyViewAttributeClass = propertyViewAttributeMappings.get(propertyAttribute);
		
		return viewAttributeInstantiator.newPropertyViewAttribute(propertyViewAttributeClass, view, attributeValue, preInitializeViews);
	}

	public Collection<String> getCommandAttributes()
	{
		return commandViewAttributeMappings.keySet();
	}

	public AbstractCommandViewAttribute<View> createCommandViewAttribute(String commandAttribute, String attributeValue)
	{
		Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass = commandViewAttributeMappings.get(commandAttribute);
		
		return viewAttributeInstantiator.newCommandViewAttribute(commandViewAttributeClass, view, attributeValue);
	}

	protected View getViewForAttribute(String attributeName)
	{
		return view;
	}
	
	public Collection<GroupedAttributeDetailsImpl> getGroupedPropertyAttributes()
	{
		return groupedViewAttributeMappings.keySet();
	}

	public AbstractGroupedViewAttribute<View> createGroupedViewAttribute(GroupedAttributeDetailsImpl groupedAttributeDetails)
	{
		Class<? extends AbstractGroupedViewAttribute<? extends View>> groupedViewAttributeClass = groupedViewAttributeMappings.get(groupedAttributeDetails);
		
		return viewAttributeInstantiator.newGroupedViewAttribute(groupedViewAttributeClass, view, preInitializeViews, groupedAttributeDetails);
	}
	
	protected View getViewForGroupedAttribute(GroupedAttributeDetailsImpl groupedAttributeDetails)
	{
		return view;
	}
	
}
