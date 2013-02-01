/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import java.util.Map;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.GroupedAttribute;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributes<T extends View>
{
	private GroupedAttribute groupedAttribute;
	private AbstractViewAttributeInstantiator viewAttributeInstantiator;
	
	private Map<String, ViewAttribute> childAttributeMap;
	private AttributeGroupBindingException bindingErrors;
	public ChildViewAttributes(GroupedAttribute groupedAttribute, AbstractViewAttributeInstantiator viewAttributeInstantiator)
	{
		this.groupedAttribute = groupedAttribute;
		this.viewAttributeInstantiator = viewAttributeInstantiator;
		
		childAttributeMap = Maps.newHashMap();
		bindingErrors = new AttributeGroupBindingException();
	}
	
	public <AttributeType extends AbstractAttribute> ChildViewAttribute<AttributeType> add(ChildViewAttribute<AttributeType> childAttribute, String attributeName)
	{
		AttributeType attribute = groupedAttribute.attributeFor(attributeName);
		childAttribute.setAttribute(attribute);
		childAttributeMap.put(attributeName, new ViewAttributeAdapter(childAttribute));
		return childAttribute;
	}
	
	public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType addProperty(
			Class<PropertyViewAttributeType> propertyViewAttributeClass, String propertyAttribute)
	{
		ValueModelAttribute attributeValue = groupedAttribute.valueModelAttributeFor(propertyAttribute);
		PropertyViewAttributeType propertyViewAttribute = viewAttributeInstantiator.newPropertyViewAttribute(propertyViewAttributeClass, attributeValue);
		childAttributeMap.put(propertyAttribute, propertyViewAttribute);
		return propertyViewAttribute;
	}
	
	void preinitializeView(BindingContext bindingContext)
	{
		for(ViewAttribute viewAttribute : childAttributeMap.values())
		{
			viewAttribute.preinitializeView(bindingContext);
		}
	}
	
	void bindTo(BindingContext bindingContext)
	{
		for(Map.Entry<String, ViewAttribute> childAttributeEntry : childAttributeMap.entrySet())
		{
			ViewAttribute childAttribute = childAttributeEntry.getValue();
			
			try
			{
				childAttribute.bindTo(bindingContext);
			}catch(RuntimeException e)
			{
				bindingErrors.addChildAttributeError(childAttributeEntry.getKey(), e);
			}
		}
		
		bindingErrors.assertNoErrors();
	}
	
	public boolean hasAttribute(String attributeName)
	{
		return groupedAttribute.hasAttribute(attributeName);
	}

	public ValueModelAttribute valueModelAttributeFor(String attributeName)
	{
		return groupedAttribute.valueModelAttributeFor(attributeName);
	}

	public StaticResourceAttribute staticResourceAttributeFor(String attributeName)
	{
		return groupedAttribute.staticResourceAttributeFor(attributeName);
	}
	
	public <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName)
	{
		return groupedAttribute.enumAttributeFor(attributeName);
	}
	
	private static class ViewAttributeAdapter implements ViewAttribute
	{
		private ChildViewAttribute<?> childViewAttribute;
		public ViewAttributeAdapter(ChildViewAttribute<?> childViewAttribute)
		{
			this.childViewAttribute = childViewAttribute;
		}
		
		@Override
		public void preinitializeView(BindingContext bindingContext)
		{
		}
		
		@Override
		public void bindTo(BindingContext bindingContext)
		{
			childViewAttribute.bindTo(bindingContext);
		}
	}
}
