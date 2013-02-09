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

import java.util.Map;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ChildAttributeResolverMapper;
import org.robobinding.attribute.GroupedAttribute;
import org.robobinding.attribute.GroupedAttributeDescriptor;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute, ChildAttributeResolverMapper
{
	private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];
	
	protected T view;
	protected GroupedAttribute groupedAttribute;
	private ViewListenersProvider viewListenersProvider;
	private AbstractViewAttributeInstantiator viewAttributeInstantiator;
	
	public void setView(T view)
	{
		this.view = view;
	}

	public void setGroupedAttributeDescriptor(GroupedAttributeDescriptor groupedAttributeDescriptor)
	{
		groupedAttributeDescriptor.assertAttributesArePresent(getCompulsoryAttributes());
		groupedAttribute = new GroupedAttribute(groupedAttributeDescriptor, this);
		validateResolvedChildAttributes();
	}

	public void setViewListenersProvider(ViewListenersProvider viewListenersProvider)
	{
		this.viewListenersProvider = viewListenersProvider;
	}
	
	protected String[] getCompulsoryAttributes()
	{
		return NO_COMPULSORY_ATTRIBUTES;
	}
	
	@Override
	public void validateResolvedChildAttributes() {
		
	}
	
	@Override
	public final void bindTo(BindingContext bindingContext)
	{
		AttributeGroupBindingException bindingErrors = new AttributeGroupBindingException();
		preBind(bindingContext);
		
		ChildAttributeBindings binding = new ChildAttributeBindings(bindingContext, bindingErrors);
		setupChildAttributeBindings(binding);
		
		binding.perform();
		bindingErrors.assertNoErrors();
		
		postBind(bindingContext);
		bindingErrors.assertNoErrors();
	}
	
	protected void preBind(BindingContext bindingContext)
	{
		
	}
	
	protected abstract void setupChildAttributeBindings(ChildAttributeBindings binding);
	
	protected void postBind(BindingContext bindingContext)
	{
		
	}
	
	private AbstractViewAttributeInstantiator safeGetViewAttributeInstantiator()
	{
		if (viewAttributeInstantiator == null)
		{
			viewAttributeInstantiator = new ViewAttributeInstantiator();
			viewAttributeInstantiator.setViewListenersIfRequired(this, view);
		}
		return viewAttributeInstantiator;
	}
	
	protected class ChildAttributeBindings
	{
		private BindingContext bindingContext;
		Map<String, ViewAttribute> childAttributeMap;
		private AttributeGroupBindingException bindingErrors;
		ChildAttributeBindings(BindingContext bindingContext, AttributeGroupBindingException bindingErrors)
		{
			this.bindingContext = bindingContext;
			this.bindingErrors = bindingErrors;
			childAttributeMap = Maps.newHashMap();
		}
		
		public <AttributeType extends AbstractAttribute> ChildViewAttribute<AttributeType> add(ChildViewAttribute<AttributeType> childAttribute, String attributeName)
		{
			AttributeType attribute = groupedAttribute.attributeFor(attributeName);
			childAttribute.setAttribute(attribute);
			childAttributeMap.put(attributeName, childAttribute);
			return childAttribute;
		}
		
		public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType addProperty(
				Class<PropertyViewAttributeType> propertyViewAttributeClass, String propertyAttribute)
		{
			ValueModelAttribute attributeValue = groupedAttribute.valueModelAttributeFor(propertyAttribute);
			PropertyViewAttributeType propertyViewAttribute = safeGetViewAttributeInstantiator().newPropertyViewAttribute(propertyViewAttributeClass, attributeValue);
			childAttributeMap.put(propertyAttribute, propertyViewAttribute);
			return propertyViewAttribute;
		}
		
		public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType addProperty(
				PropertyViewAttributeType propertyViewAttribute, String propertyAttribute)
		{
			ValueModelAttribute attributeValue = groupedAttribute.valueModelAttributeFor(propertyAttribute);
			propertyViewAttribute = safeGetViewAttributeInstantiator().injectProperties(propertyViewAttribute, attributeValue);
			childAttributeMap.put(propertyAttribute, propertyViewAttribute);
			return propertyViewAttribute;
		}
		
		private void perform()
		{
			bindChildAttributes();
		}
	
		private void bindChildAttributes()
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
		}
	}

	private class ViewAttributeInstantiator extends AbstractViewAttributeInstantiator
	{
		public ViewAttributeInstantiator()
		{
			super(AbstractGroupedViewAttribute.this.viewListenersProvider);
		}
		@Override
		protected T getView()
		{
			return view;
		}
	}
}
