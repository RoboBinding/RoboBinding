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

import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.GroupedAttributeDetailsImpl;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class CustomBindingAttributeMappingsImpl<T extends View> extends BindingAttributeMappingsImpl<T> implements CustomBindingAttributeMappings<T>
{
	private Map<String, View> customAttributeViews;
	
	public CustomBindingAttributeMappingsImpl(T view, boolean preInitializeViews)
	{
		super(view, preInitializeViews);
		
		customAttributeViews = Maps.newHashMap();
	}

	@Override
	protected void addPropertyViewAttributeMapping(Class<? extends PropertyViewAttribute<?>> propertyViewAttributeClass, String attributeName)
	{
		Validate.notBlank(attributeName, "attribute name must not be empty");
		Validate.notNull(propertyViewAttributeClass, "propertyViewAttributeClass must not be null");
		super.addPropertyViewAttributeMapping(propertyViewAttributeClass, attributeName);
	}
	
	@Override
	protected void addCommandViewAttributeMapping(Class<? extends AbstractCommandViewAttribute<?>> commandViewAttributeClass, String attributeName)
	{
		Validate.notBlank(attributeName, "attribute name must not be empty");
		Validate.notNull(commandViewAttributeClass, "commandViewAttributeClass must not be null");
		super.addCommandViewAttributeMapping(commandViewAttributeClass, attributeName);
	}
	
	@Override
	protected void addGroupedViewAttributeMapping(Class<? extends AbstractGroupedViewAttribute<?>> groupedViewAttributeClass,	String... attributeNames)
	{
		Validate.notNull(attributeNames, "attribute names must not be null");
		Validate.notNull(groupedViewAttributeClass, "groupedViewAttributeClass must not be null");
		super.addGroupedViewAttributeMapping(groupedViewAttributeClass, attributeNames);
	}
	
	@Override
	public <S extends View> void mapPropertyAttribute(S alternateView, Class<? extends PropertyViewAttribute<? extends View>> propertyViewAttributeClass,
			String attributeName)
	{
		Validate.notNull(alternateView, "view must not be null");
		addPropertyViewAttributeMapping(propertyViewAttributeClass, attributeName);
		customAttributeViews.put(attributeName, alternateView);
	}

	@Override
	public <S extends View> void mapCommandAttribute(S alternateView, Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass,
			String attributeName)
	{
		Validate.notNull(alternateView, "view must not be null");
		addCommandViewAttributeMapping(commandViewAttributeClass, attributeName);
		customAttributeViews.put(attributeName, alternateView);
	}

	@Override
	public <S extends View> void mapGroupedAttribute(S alternateView, Class<? extends AbstractGroupedViewAttribute<? extends View>> groupedViewAttributeClass,
			String... attributeNames)
	{
		Validate.notNull(alternateView, "view must not be null");
		addGroupedViewAttributeMapping(groupedViewAttributeClass, attributeNames);
		customAttributeViews.put(attributeNames[0], alternateView);
	}

	@Override
	protected View getViewForAttribute(String attributeName)
	{
		if (customAttributeViews.containsKey(attributeName))
			return customAttributeViews.get(attributeName);
		
		return super.getViewForAttribute(attributeName);
	}
	
	@Override
	protected View getViewForGroupedAttribute(GroupedAttributeDetailsImpl groupedAttributeDetails)
	{
		String identifyingAttributeName = groupedAttributeDetails.getSupportedAttributes()[0];
		
		if (customAttributeViews.containsKey(identifyingAttributeName))
			return customAttributeViews.get(identifyingAttributeName);
			
		return super.getViewForGroupedAttribute(groupedAttributeDetails);
	}
}
