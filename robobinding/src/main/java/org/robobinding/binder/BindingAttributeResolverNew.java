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
package org.robobinding.binder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.robobinding.binders.BindingContext;
import org.robobinding.binders.ViewPendingAttributes;
import org.robobinding.binders.ViewPendingAttributes.AttributeGroupResolver;
import org.robobinding.binders.ViewPendingAttributes.AttributeResolver;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.MalformedBindingAttributeException;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewListenersProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.GroupedAttributeDetailsImpl;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class BindingAttributeResolverNew
{
	private final BindingAttributeProvidersResolver providersResolver;
	private ViewBindingAttributes viewBindingAttributes;
	private ViewPendingAttributes viewPendingAttributes;
	private PropertyViewAttributeResolver propertyViewAttributeResolver;
	private CommandViewAttributeResolver commandViewAttributeResolver;
	private GroupedViewAttributeResolver groupedViewAttributeResolver;

	public BindingAttributeResolverNew()
	{
		this.providersResolver = new BindingAttributeProvidersResolver();
	}

	public ViewBindingAttributes resolve(ViewPendingAttributes viewPendingAttributes)
	{
		viewBindingAttributes = new ViewBindingAttributes();
		ViewListenersProvider viewListenersProvider = new ViewListenersProviderImpl();
		Collection<BindingAttributeProvider<? extends View>> providers = providersResolver.getCandidateProviders(viewPendingAttributes.getView());
		
		for (BindingAttributeProvider<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<View> bindingAttributeProvider = (BindingAttributeProvider<View>)provider;
			BindingAttributeMappingsImpl<View> bindingAttributeMappings = bindingAttributeProvider.createBindingAttributeMappings(
					viewPendingAttributes.getView(), viewListenersProvider);
			resolve(bindingAttributeMappings);
			
			if (viewPendingAttributes.isEmpty())
				break;
		}
		
		viewPendingAttributes.assertAllResolved();
		
		return viewBindingAttributes;
	}
	
	void resolve(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		resolvePropertyViewAttributes(viewAttributeMappings);
		resolveCommandViewAttributes(viewAttributeMappings);
		resolveGroupedViewAttributes(viewAttributeMappings);
	}

	private void resolvePropertyViewAttributes(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String propertyAttribute : viewAttributeMappings.getPropertyAttributes())
		{
			viewPendingAttributes.resolveAttributeIfExists(propertyAttribute, propertyViewAttributeResolver);
		}
	}

	private void resolveCommandViewAttributes(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String commandAttribute : viewAttributeMappings.getCommandAttributes())
		{
			viewPendingAttributes.resolveAttributeIfExists(commandAttribute, commandViewAttributeResolver);
		}
	}

	private void resolveGroupedViewAttributes(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (GroupedAttributeDetailsImpl groupedAttributeDetails : viewAttributeMappings.getGroupedPropertyAttributes())
		{
			viewPendingAttributes.resolveAttributeGroupIfExists(attributeGroup, groupedViewAttributeResolver);
		}
	}

	private class PropertyViewAttributeResolver implements AttributeResolver
	{
		@Override
		public void resolve(View view, String attribute, String attributeValue)
		{
			PropertyViewAttribute<View> propertyViewAttribute = viewAttributeMappings.createPropertyViewAttribute(
					attribute, attributeValue);
			viewBindingAttributes.addResolvedViewAttribute(propertyViewAttribute);
		}
	}
	
	private class CommandViewAttributeResolver implements AttributeResolver
	{
		@Override
		public void resolve(View view, String attribute, String attributeValue)
		{
			AbstractCommandViewAttribute<View> commandViewAttribute = viewAttributeMappings.createCommandViewAttribute(
					attribute, attributeValue);
			viewBindingAttributes.addResolvedViewAttribute(commandViewAttribute);
		}
	}
	
	private class GroupedViewAttributeResolver implements AttributeGroupResolver
	{
		@Override
		public void resolve(View view, Map<String, String> presentAttributeMappings)
		{
			AbstractGroupedViewAttribute<View> groupedViewAttribute = viewAttributeMappings.createGroupedViewAttribute(groupedAttributeDetails);
			viewBindingAttributes.addResolvedViewAttribute(groupedViewAttribute);
		}
	}
	
	static class ViewBindingAttributes
	{
		private final List<ViewAttribute> viewAttributes;
		
		private ViewBindingAttributes()
		{
			this.viewAttributes = Lists.newArrayList();
		}
		
		private void addResolvedViewAttribute(ViewAttribute viewAttribute)
		{
			viewAttributes.add(viewAttribute);
		}
		
		public void bindTo(BindingContext context)
		{
			for (ViewAttribute viewAttribute : viewAttributes)
				viewAttribute.bindTo(context);
		}
	}
}
