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
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.GroupedAttributeDetails;
import org.robobinding.attribute.ValueModelAttribute;

import com.google.common.collect.Maps;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute
{
	private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];
	
	protected T view;
	protected GroupedAttributeDetails groupedAttributeDetails;
	private ViewListenersProvider viewListenersProvider;
	private AbstractViewAttributeInstantiator viewAttributeInstantiator;
	
	public void setView(T view)
	{
		this.view = view;
	}

	public void setGroupedAttributeDetails(GroupedAttributeDetails groupedAttributeDetails)
	{
		groupedAttributeDetails.assertAttributesArePresent(getCompulsoryAttributes());
		this.groupedAttributeDetails = groupedAttributeDetails;
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
	public final void bindTo(BindingContext bindingContext)
	{
		AttributeGroupBindingException bindingErrors = new AttributeGroupBindingException();
		
		try
		{
			preBind(bindingContext);
		}catch(RuntimeException e)
		{
			bindingErrors.addGeneralError(e.getMessage());
			throw bindingErrors;
		}
		
		ChildAttributesBinding binding = new ChildAttributesBinding(bindingContext, bindingErrors);
		try
		{
			setupChildAttributesBinding(binding);
		}catch(RuntimeException e)
		{
			bindingErrors.addGeneralError(e.getMessage());
		}
		binding.perform();
		bindingErrors.assertNoErrors();
		
		postBind(bindingContext);
		bindingErrors.assertNoErrors();
	}
	
	protected void preBind(BindingContext bindingContext)
	{
		
	}
	
	protected abstract void setupChildAttributesBinding(ChildAttributesBinding binding);
	
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
	
	protected class ChildAttributesBinding
	{
		private BindingContext bindingContext;
		private Map<String, ViewAttribute> childAttributeMap;
		private AttributeGroupBindingException bindingErrors;
		private ChildAttributesBinding(BindingContext bindingContext, AttributeGroupBindingException bindingErrors)
		{
			this.bindingContext = bindingContext;
			this.bindingErrors = bindingErrors;
			childAttributeMap = Maps.newHashMap();
		}
		
		public ChildAttribute add(ChildAttribute childAttribute, String attribute)
		{
			childAttributeMap.put(attribute, childAttribute);
			return childAttribute;
		}
		
		public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType addProperty(
				Class<PropertyViewAttributeType> propertyViewAttributeClass, String propertyAttribute)
		{
			ValueModelAttribute attributeValue = groupedAttributeDetails.valueModelAttributeFor(propertyAttribute);
			PropertyViewAttributeType propertyViewAttribute = safeGetViewAttributeInstantiator().newPropertyViewAttribute(propertyViewAttributeClass, attributeValue);
			childAttributeMap.put(propertyAttribute, propertyViewAttribute);
			return propertyViewAttribute;
		}
		
		public <CommandViewAttributeType extends AbstractCommandViewAttribute<T>> CommandViewAttributeType addCommand(
				Class<CommandViewAttributeType> commandViewAttributeClass, String commandAttribute)
		{
			CommandAttribute attributeValue = groupedAttributeDetails.commandAttributeFor(commandAttribute);
			CommandViewAttributeType commandViewAttribute = safeGetViewAttributeInstantiator().newCommandViewAttribute(commandViewAttributeClass, attributeValue);
			childAttributeMap.put(commandAttribute, commandViewAttribute);
			return commandViewAttribute;
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
					bindingErrors.addChildAttributeError(childAttributeEntry.getKey(), e.getMessage());
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
