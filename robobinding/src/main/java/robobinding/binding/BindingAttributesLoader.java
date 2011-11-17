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
package robobinding.binding;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import robobinding.binding.viewattribute.provider.BindingAttributeProvider;
import robobinding.internal.com_google_common.collect.Lists;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class BindingAttributesLoader
{
	private final ProvidersResolver providersResolver;
	private final AttributeSetParser attributeSetParser;
	private final boolean preInitializeViews;
	
	BindingAttributesLoader(ProvidersResolver providersResolver, AttributeSetParser attributeSetParser, boolean preInitializeViews)
	{
		this.providersResolver = providersResolver;
		this.attributeSetParser = attributeSetParser;
		this.preInitializeViews = preInitializeViews;
	}

	ViewBindingAttributes load(View view, AttributeSet attrs)
	{
		List<BindingAttribute> bindingAttributes = determineBindingAttributes(view, attrs);
		return new ViewBindingAttributes(bindingAttributes);
	}
	
	private List<BindingAttribute> determineBindingAttributes(View view, AttributeSet attrs)
	{
		List<BindingAttribute> bindingAttributes = Lists.newArrayList();
		Map<String, String> pendingBindingAttributes = attributeSetParser.loadBindingAttributes(attrs);
		Collection<BindingAttributeProvider<? extends View>> providers = providersResolver.getCandidateProviders(view);
		
		for (BindingAttributeProvider<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<View> viewProvider = (BindingAttributeProvider<View>)provider;
			List<BindingAttribute> newBindingAttributes = viewProvider.createSupportedBindingAttributes(view, pendingBindingAttributes, preInitializeViews);
			removeProcessedAttributes(newBindingAttributes, pendingBindingAttributes);
			bindingAttributes.addAll(newBindingAttributes);
		}
		
		if (!pendingBindingAttributes.isEmpty())
			throw new RuntimeException("Unhandled binding attribute(s): " + describeUnhandledAttributes(pendingBindingAttributes));
		
		return bindingAttributes;
	}
	
	private void removeProcessedAttributes(List<BindingAttribute> newBindingAttributes, Map<String, String> pendingBindingAttributes)
	{
		for (BindingAttribute bindingAttribute : newBindingAttributes)
		{
			for (String attributeName : bindingAttribute.getAttributeNames())
				pendingBindingAttributes.remove(attributeName);
		}
	}
	
	private String describeUnhandledAttributes(Map<String, String> pendingBindingAttributes)
	{
		String unhandledAttributes = "";
		
		for (String attributeKey : pendingBindingAttributes.keySet())
			unhandledAttributes += attributeKey + ": " + pendingBindingAttributes.get(attributeKey) + "; ";
				
		return unhandledAttributes;
	}
	
	static class ViewBindingAttributes
	{
		final List<BindingAttribute> bindingAttributes;
		
		ViewBindingAttributes(List<BindingAttribute> bindingAttributes)
		{
			this.bindingAttributes = bindingAttributes;
		}
		
		public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
		{
			for (BindingAttribute bindingAttribute : bindingAttributes)
				bindingAttribute.bind(presentationModelAdapter, context);
		}
	}
}
