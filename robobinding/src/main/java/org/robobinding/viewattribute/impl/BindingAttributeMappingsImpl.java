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

import static org.robobinding.viewattribute.FromClassViewAttributeFactory.viewAttributeFactoryForClass;

import java.util.Map;

import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttributeFactory;

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
	private final ViewAttributeInitializer viewAttributeInitializer;
	private final PropertyAttributeParser propertyAttributeValueParser;

	private final Map<String, ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>>> propertyViewAttributeMappings;
	private final Map<String, ViewAttributeFactory<? extends AbstractCommandViewAttribute<? extends View>>> commandViewAttributeMappings;
	private final Map<String[], ViewAttributeFactory<? extends AbstractGroupedViewAttribute<? extends View>>> groupedViewAttributeMappings;
	
	public BindingAttributeMappingsImpl(ViewAttributeInitializer viewAttributeInitializer)
	{
		this.viewAttributeInitializer = viewAttributeInitializer;
		propertyAttributeValueParser = new PropertyAttributeParser();
		
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
	
	@Override
	public void mapGroupedAttribute(ViewAttributeFactory<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeFactory, String... attributeNames)
	{
		addGroupedViewAttributeMapping(groupedViewAttributeFactory, attributeNames);
	}

	protected void addPropertyViewAttributeMapping(Class<? extends PropertyViewAttribute<?>> propertyViewAttributeClass, String attributeName)
	{
		propertyViewAttributeMappings.put(attributeName, viewAttributeFactoryForClass(propertyViewAttributeClass));
	}
	
	protected void addCommandViewAttributeMapping(Class<? extends AbstractCommandViewAttribute<?>> commandViewAttributeClass, String attributeName)
	{
		commandViewAttributeMappings.put(attributeName, viewAttributeFactoryForClass(commandViewAttributeClass));
	}
	
	protected void addGroupedViewAttributeMapping(Class<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeClass, String... attributeNames)
	{
		groupedViewAttributeMappings.put(attributeNames, viewAttributeFactoryForClass(groupedViewAttributeClass));
	}
	
	protected void addGroupedViewAttributeMapping(ViewAttributeFactory<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeFactory, String... attributeNames)
	{
		groupedViewAttributeMappings.put(attributeNames, groupedViewAttributeFactory);
	}
	
	public Iterable<String> getPropertyAttributes()
	{
		return propertyViewAttributeMappings.keySet();
	}

	public PropertyViewAttribute<View> createPropertyViewAttribute(T defaultView, String propertyAttribute, String attributeValue)
	{
		@SuppressWarnings("unchecked")
		PropertyViewAttribute<View> propertyViewAttribute = (PropertyViewAttribute<View>)propertyViewAttributeMappings.get(propertyAttribute).create();
		View view = getViewForAttribute(propertyAttribute, defaultView);
		propertyViewAttribute = viewAttributeInitializer.initializePropertyViewAttribute(
				view, propertyViewAttribute, propertyAttributeValueParser.parseAsValueModelAttribute(propertyAttribute, attributeValue));
		return propertyViewAttribute;
	}

	public Iterable<String> getCommandAttributes()
	{
		return commandViewAttributeMappings.keySet();
	}

	public AbstractCommandViewAttribute<View> createCommandViewAttribute(View defaultView, String commandAttribute, String attributeValue)
	{
		@SuppressWarnings("unchecked")
		AbstractCommandViewAttribute<View> commandViewAttribute = (AbstractCommandViewAttribute<View>)commandViewAttributeMappings.get(commandAttribute).create();
		View view = getViewForAttribute(commandAttribute, defaultView);
		commandViewAttribute = viewAttributeInitializer.initializeCommandViewAttribute(
				view, commandViewAttribute, new CommandAttribute(commandAttribute, attributeValue));
		return commandViewAttribute;
	}

	protected View getViewForAttribute(String attributeName, View defaultView)
	{
		return defaultView;
	}
	
	public Iterable<String[]> getAttributeGroups()
	{
		return groupedViewAttributeMappings.keySet();
	}

	public AbstractGroupedViewAttribute<View> createGroupedViewAttribute(View defaultView, String[] attributeGroup, Map<String, String> presentAttributeMappings)
	{
		@SuppressWarnings("unchecked")
		AbstractGroupedViewAttribute<View> groupedViewAttribute = (AbstractGroupedViewAttribute<View>)groupedViewAttributeMappings.get(attributeGroup).create();
		View view = getViewForAttributeGroup(attributeGroup, defaultView);
		PendingGroupAttributes groupedAttributeDescriptor = new PendingGroupAttributes(presentAttributeMappings);
		groupedViewAttribute = viewAttributeInitializer.initializeGroupedViewAttribute(
				view, groupedViewAttribute, groupedAttributeDescriptor);
		return groupedViewAttribute;
	}
	
	protected View getViewForAttributeGroup(String[] attributeGroup, View defaultView)
	{
		return defaultView;
	}
}
