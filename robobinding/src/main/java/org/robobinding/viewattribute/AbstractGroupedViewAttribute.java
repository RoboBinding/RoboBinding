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

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.Map;

import org.robobinding.AttributeResolutionException;
import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ChildAttributeResolverMapper;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.GroupAttributes;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

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
	protected GroupAttributes groupAttributes;
	private ViewListenersProvider viewListenersProvider;
	private AbstractViewAttributeInitializer viewAttributeInitializer;
	
	public void setView(T view)
	{
		this.view = view;
	}
	
	public void resolvePendingGroupAttributes(PendingGroupAttributes pendingGroupAttributes)
	{
		pendingGroupAttributes.assertAttributesArePresent(getCompulsoryAttributes());
		ChildAttributeResolverMappings resolverMappings = createResolverMappings();
		groupAttributes = new GroupAttributes(pendingGroupAttributes, resolverMappings);
		validateResolvedChildAttributes();
	}
	
	private ChildAttributeResolverMappings createResolverMappings()
	{
		ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
		mapChildAttributeResolvers(resolverMappings);
		return resolverMappings;
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
	public void validateResolvedChildAttributes() throws AttributeResolutionException {
		
	}
	
	@Override
	public final void bindTo(BindingContext bindingContext)
	{
		AttributeGroupBindingException bindingErrors = new AttributeGroupBindingException();
		preBind(bindingContext); //is pre-bind necessary?
		
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
	
	private AbstractViewAttributeInitializer safeGetViewAttributeInitializer()
	{
		if (viewAttributeInitializer == null)
		{
			viewAttributeInitializer = new ViewAttributeInitializer();
			viewAttributeInitializer.setViewListenersIfRequired(this, view);
		}
		return viewAttributeInitializer;
	}
	
	protected class ChildAttributeBindings
	{
		private BindingContext bindingContext;
		Map<String, ViewAttribute> childAttributeMap;
		private AttributeGroupBindingException bindingErrors;
		private boolean failOnFirstBindingError;
		ChildAttributeBindings(BindingContext bindingContext, AttributeGroupBindingException bindingErrors)
		{
			this.bindingContext = bindingContext;
			this.bindingErrors = bindingErrors;
			childAttributeMap = newLinkedHashMap();
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ViewAttribute add(String attributeName, ViewAttribute viewAttribute)
		{
			if (viewAttribute instanceof ChildViewAttribute)
			{
				AbstractAttribute attribute = groupAttributes.attributeFor(attributeName);
				((ChildViewAttribute)viewAttribute).setAttribute(attribute);
			}
			childAttributeMap.put(attributeName, viewAttribute);
			return viewAttribute;
		}
		
		public <PropertyViewAttributeType extends PropertyViewAttribute<T>> PropertyViewAttributeType addProperty(
				String propertyAttribute, PropertyViewAttributeType propertyViewAttribute)
		{
			ValueModelAttribute attributeValue = groupAttributes.valueModelAttributeFor(propertyAttribute);
			propertyViewAttribute = safeGetViewAttributeInitializer().injectProperties(propertyViewAttribute, attributeValue);
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
				} catch(RuntimeException e)
				{
					addChildAttributeError(childAttributeEntry.getKey(), e);
					
					if (failOnFirstBindingError)
						return;
				}
			}
		}

		private void addChildAttributeError(String attributeName, RuntimeException e)
		{
			bindingErrors.addChildAttributeError(attributeName, e);
		}

		public void failOnFirstBindingError()
		{
			failOnFirstBindingError = true;
		}
	}

	private class ViewAttributeInitializer extends AbstractViewAttributeInitializer
	{
		public ViewAttributeInitializer()
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
