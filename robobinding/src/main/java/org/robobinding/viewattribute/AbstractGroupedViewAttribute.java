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

import java.util.List;
import java.util.Map;

import org.robobinding.AttributeBindingException;
import org.robobinding.BindingContext;
import org.robobinding.attributevalue.CommandAttributeValue;
import org.robobinding.attributevalue.GroupedAttributeDetails;
import org.robobinding.attributevalue.ValueModelAttributeValue;

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
		preBind(bindingContext);
		
		ChildAttributesBinding binding = new ChildAttributesBinding(bindingContext);
		setupChildAttributesBinding(binding);
		binding.perform();
		
		postBind(bindingContext);
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
		private final BindingContext bindingContext;
		private Map<String, ViewAttribute> childAttributeMap;
		private List<AttributeBindingException> attributeBindingExceptions;
		private ChildAttributesBinding(BindingContext bindingContext)
		{
			this.bindingContext = bindingContext;
		}
		
		public ChildAttribute add(ChildAttribute childAttribute, String attribute)
		{
			childAttributeMap.put(attribute, childAttribute);
			return childAttribute;
		}
		
		public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType addProperty(
				Class<PropertyViewAttributeType> propertyViewAttributeClass, String propertyAttribute)
		{
			ValueModelAttributeValue attributeValue = groupedAttributeDetails.valueModelAttributeValueFor(propertyAttribute);
			PropertyViewAttributeType propertyViewAttribute = safeGetViewAttributeInstantiator().newPropertyViewAttribute(propertyViewAttributeClass, attributeValue);
			childAttributeMap.put(propertyAttribute, propertyViewAttribute);
			return propertyViewAttribute;
		}
		
		public <CommandViewAttributeType extends AbstractCommandViewAttribute<T>> CommandViewAttributeType addCommand(
				Class<CommandViewAttributeType> commandViewAttributeClass, String commandAttribute)
		{
			CommandAttributeValue attributeValue = groupedAttributeDetails.commandAttributeValueFor(commandAttribute);
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
				}catch(AttributeBindingException e)
				{
					attributeBindingExceptions.add(e);
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
