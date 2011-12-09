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
public class BindingAttributesProcessor
{
	private final ProvidersResolver providersResolver;
	private final AttributeSetParser attributeSetParser;
	private final boolean preInitializeViews;
	
	public BindingAttributesProcessor(boolean preInitializeViews)
	{
		this.preInitializeViews = preInitializeViews;
		this.providersResolver = new ProvidersResolver();
		this.attributeSetParser = new AttributeSetParser();
	}

	public ViewBindingAttributes read(View view, AttributeSet attrs)
	{
		Map<String, String> pendingBindingAttributes = attributeSetParser.parse(attrs);
		return process(view, pendingBindingAttributes);
	}
	
	public ViewBindingAttributes process(View view, Map<String, String> pendingBindingAttributes)
	{
		List<BindingAttribute> bindingAttributes = determineBindingAttributes(view, pendingBindingAttributes);
		return new ViewBindingAttributes(bindingAttributes);
	}
	
	private List<BindingAttribute> determineBindingAttributes(View view, Map<String, String> pendingBindingAttributes)
	{
		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(pendingBindingAttributes);
		Collection<BindingAttributeProvider<? extends View>> providers = providersResolver.getCandidateProviders(view);
		
		for (BindingAttributeProvider<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<View> viewProvider = (BindingAttributeProvider<View>)provider;
			viewProvider.resolveSupportedBindingAttributes(view, bindingAttributeResolver, preInitializeViews);
			
			if (bindingAttributeResolver.isDone())
				break;
		}
		
		if (bindingAttributeResolver.hasUnresolvedAttributes())
			throw new RuntimeException("Unhandled binding attribute(s): " + bindingAttributeResolver.describeUnresolvedAttributes());
		
		return bindingAttributeResolver.getResolvedBindingAttributes();
	}
	
	public static class ViewBindingAttributes
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
