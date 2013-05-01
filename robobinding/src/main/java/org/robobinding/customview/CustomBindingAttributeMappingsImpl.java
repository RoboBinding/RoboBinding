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
package org.robobinding.customview;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;
import static org.robobinding.util.Preconditions.checkNotBlank;

import java.util.Map;

import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttributeFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.ViewAttributeInitializer;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class CustomBindingAttributeMappingsImpl<T extends View> extends BindingAttributeMappingsImpl<T> implements CustomBindingAttributeMappings<T>
{
	private final Map<String, View> customAttributeViews = newHashMap();
	
	public CustomBindingAttributeMappingsImpl(ViewAttributeInitializer viewAttributeInitializer)
	{
		super(viewAttributeInitializer);
	}

	@Override
	protected void addPropertyViewAttributeMapping(Class<? extends PropertyViewAttribute<?>> propertyViewAttributeClass, String attributeName)
	{
		checkNotBlank(attributeName, "attribute name must not be empty");
		checkNotNull(propertyViewAttributeClass, "propertyViewAttributeClass must not be null");
		super.addPropertyViewAttributeMapping(propertyViewAttributeClass, attributeName);
	}
	
	@Override
	protected void addCommandViewAttributeMapping(Class<? extends AbstractCommandViewAttribute<?>> commandViewAttributeClass, String attributeName)
	{
		checkNotBlank(attributeName, "attribute name must not be empty");
		checkNotNull(commandViewAttributeClass, "commandViewAttributeClass must not be null");
		super.addCommandViewAttributeMapping(commandViewAttributeClass, attributeName);
	}
	
	@Override
	protected void addGroupedViewAttributeMapping(Class<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeClass,	String... attributeNames)
	{
		checkNotNull(attributeNames, "attribute names must not be null");
		checkNotNull(groupedViewAttributeClass, "groupedViewAttributeClass must not be null");
		super.addGroupedViewAttributeMapping(groupedViewAttributeClass, attributeNames);
	}
	
	@Override
	protected void addGroupedViewAttributeMapping(ViewAttributeFactory<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeFactory,	String... attributeNames)
	{
		checkNotNull(attributeNames, "attribute names must not be null");
		checkNotNull(groupedViewAttributeFactory, "groupedViewAttributeFactory must not be null");
		super.addGroupedViewAttributeMapping(groupedViewAttributeFactory, attributeNames);
	}
	
	@Override
	public <S extends View> void mapPropertyAttribute(S alternateView, Class<? extends PropertyViewAttribute<? extends View>> propertyViewAttributeClass,
			String attributeName)
	{
		checkNotNull(alternateView, "view must not be null");
		addPropertyViewAttributeMapping(propertyViewAttributeClass, attributeName);
		customAttributeViews.put(attributeName, alternateView);
	}

	@Override
	public <S extends View> void mapCommandAttribute(S alternateView, Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass,
			String attributeName)
	{
		checkNotNull(alternateView, "view must not be null");
		addCommandViewAttributeMapping(commandViewAttributeClass, attributeName);
		customAttributeViews.put(attributeName, alternateView);
	}

	@Override
	public <S extends View> void mapGroupedAttribute(S alternateView, Class<? extends AbstractGroupedViewAttribute<? extends View>> groupedViewAttributeClass,
			String... attributeNames)
	{
		checkNotNull(alternateView, "view must not be null");
		addGroupedViewAttributeMapping(groupedViewAttributeClass, attributeNames);
		customAttributeViews.put(attributeNames[0], alternateView);
	}

	@Override
	public <S extends View> void mapGroupedAttribute(S alternateView, ViewAttributeFactory<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeFactory, String... attributeNames)
	{
		checkNotNull(alternateView, "view must not be null");
		addGroupedViewAttributeMapping(groupedViewAttributeFactory, attributeNames);
		customAttributeViews.put(attributeNames[0], alternateView);
	}
	
	@Override
	protected View getViewForAttribute(String attributeName, View defaultView)
	{
		if (customAttributeViews.containsKey(attributeName))
			return customAttributeViews.get(attributeName);
		
		return super.getViewForAttribute(attributeName, defaultView);
	}
	
	@Override
	protected View getViewForAttributeGroup(String[] attributeGroup, View defaultView)
	{
		String identifyingAttributeName = attributeGroup[0];
		
		if (customAttributeViews.containsKey(identifyingAttributeName))
			return customAttributeViews.get(identifyingAttributeName);
			
		return super.getViewForAttributeGroup(attributeGroup, defaultView);
	}
}
