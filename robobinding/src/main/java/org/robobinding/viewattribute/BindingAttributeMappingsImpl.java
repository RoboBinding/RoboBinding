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

import android.view.View;

import com.google.common.collect.Maps;

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
	private ViewListenersProvider viewListenersProvider;
	
	private ViewAttributeInstantiator viewAttributeInstantiator;
	private Map<String, Class<? extends PropertyViewAttribute<? extends View>>> propertyViewAttributeMappings;
	private Map<String, Class<? extends AbstractCommandViewAttribute<? extends View>>> commandViewAttributeMappings;
	private Map<GroupedAttributeDetailsImpl, Class<? extends AbstractGroupedViewAttribute<? extends View>>> groupedViewAttributeMappings;
	
	public BindingAttributeMappingsImpl(T view, boolean preInitializeViews, ViewListenersProvider viewListenersProvider)
	{
		this.view = view;
		this.preInitializeViews = preInitializeViews;
		this.viewListenersProvider = viewListenersProvider;
		
		viewAttributeInstantiator = new ViewAttributeInstantiator();
		
		propertyViewAttributeMappings = Maps.newHashMap();
		commandViewAttributeMappings = Maps.newHashMap();
		groupedViewAttributeMappings = Maps.newHashMap();
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
	
	protected void addGroupedViewAttributeMapping(Class<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeClass, String... attributeNames)
	{
		GroupedAttributeDetailsImpl groupedPropertyAttribute = new GroupedAttributeDetailsImpl(attributeNames);
		addGroupedViewAttributeMapping(groupedPropertyAttribute, groupedViewAttributeClass);
	}
	
	void addGroupedViewAttributeMapping(GroupedAttributeDetailsImpl groupedPropertyAttribute, Class<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeClass)
	{
		groupedViewAttributeMappings.put(groupedPropertyAttribute, groupedViewAttributeClass);
	}
	
	public Collection<String> getPropertyAttributes()
	{
		return propertyViewAttributeMappings.keySet();
	}

	public PropertyViewAttribute<View> createPropertyViewAttribute(String propertyAttribute, String attributeValue)
	{
		Class<? extends PropertyViewAttribute<? extends View>> propertyViewAttributeClass = propertyViewAttributeMappings.get(propertyAttribute);
		@SuppressWarnings("unchecked")
		PropertyViewAttribute<View> propertyViewAttribute = (PropertyViewAttribute<View>)viewAttributeInstantiator.newPropertyViewAttribute(
				propertyViewAttributeClass, propertyAttribute, attributeValue);
		return propertyViewAttribute;
	}

	public Collection<String> getCommandAttributes()
	{
		return commandViewAttributeMappings.keySet();
	}

	public AbstractCommandViewAttribute<View> createCommandViewAttribute(String commandAttribute, String attributeValue)
	{
		Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass = commandViewAttributeMappings.get(commandAttribute);
		@SuppressWarnings("unchecked")
		AbstractCommandViewAttribute<View> commandViewAttribute = (AbstractCommandViewAttribute<View>) viewAttributeInstantiator.newCommandViewAttribute(
				commandViewAttributeClass, commandAttribute, attributeValue);
		return commandViewAttribute;
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
		@SuppressWarnings("unchecked")
		AbstractGroupedViewAttribute<View> groupedViewAttribute = (AbstractGroupedViewAttribute<View>)viewAttributeInstantiator.newGroupedViewAttribute(groupedViewAttributeClass, groupedAttributeDetails);
		return groupedViewAttribute;
	}
	
	protected View getViewForGroupedAttribute(GroupedAttributeDetailsImpl groupedAttributeDetails)
	{
		return view;
	}
	
	private class ViewAttributeInstantiator extends AbstractViewAttributeInstantiator
	{
		private String currentPropertyOrCommandAttributeValue;
		
		protected ViewAttributeInstantiator()
		{
			super(preInitializeViews, viewListenersProvider);
		}

		public <PropertyViewAttributeType extends PropertyViewAttribute<? extends View>> PropertyViewAttributeType newPropertyViewAttribute(
				Class<PropertyViewAttributeType> propertyViewAttributeClass, String propertyAttribute, String attributeValue)
		{
			currentPropertyOrCommandAttributeValue = attributeValue;
			return newPropertyViewAttribute(propertyViewAttributeClass, propertyAttribute);
		}
		
		public <CommandViewAttributeType extends AbstractCommandViewAttribute<? extends View>> CommandViewAttributeType newCommandViewAttribute(
				Class<CommandViewAttributeType> commandViewAttributeClass, String commandAttribute, String attributeValue)
		{
			currentPropertyOrCommandAttributeValue = attributeValue;
			return newCommandViewAttribute(commandViewAttributeClass, commandAttribute);
		}
		
		@SuppressWarnings("unchecked")
		public <GroupedViewAttributeType extends AbstractGroupedViewAttribute<? extends View>> GroupedViewAttributeType newGroupedViewAttribute(
				Class<GroupedViewAttributeType> groupedViewAttributeClass, GroupedAttributeDetailsImpl groupedAttributeDetails)
		{
			GroupedViewAttributeType groupedViewAttribute = (GroupedViewAttributeType)newViewAttribute(groupedViewAttributeClass);
			View view = getViewForGroupedAttribute(groupedAttributeDetails);
			((AbstractGroupedViewAttribute<View>)groupedViewAttribute).setView(view);
			groupedViewAttribute.setPreInitializeViews(preInitializeViews);
			groupedViewAttribute.setGroupedAttributeDetails(groupedAttributeDetails);
			groupedViewAttribute.setViewListenersProvider(viewListenersProvider);
			groupedViewAttribute.postInitialization();
			return groupedViewAttribute;
		}
		
		@Override
		protected View getViewForAttribute(String attributeName)
		{
			return BindingAttributeMappingsImpl.this.getViewForAttribute(attributeName);
		}

		@Override
		protected String attributeValueFor(String attribute)
		{
			return currentPropertyOrCommandAttributeValue;
		}
	}
}
