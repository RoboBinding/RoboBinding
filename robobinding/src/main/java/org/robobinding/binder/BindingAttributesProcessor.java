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
import java.util.List;
import java.util.Map;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.ViewAttribute;

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
	
	public BindingAttributesProcessor(AttributeSetParser attributeSetParser, boolean preInitializeViews)
	{
		this.preInitializeViews = preInitializeViews;
		this.providersResolver = new ProvidersResolver();
		this.attributeSetParser = attributeSetParser;
	}

	public ViewAttributes read(View view, AttributeSet attrs)
	{
		Map<String, String> pendingBindingAttributes = attributeSetParser.parse(attrs);
		return process(view, pendingBindingAttributes);
	}
	
	public ViewAttributes process(View view, Map<String, String> pendingBindingAttributes)
	{
		List<ViewAttribute> viewAttributes = determineViewAttributes(view, pendingBindingAttributes);
		return new ViewAttributes(viewAttributes);
	}
	
	private List<ViewAttribute> determineViewAttributes(View view, Map<String, String> pendingBindingAttributes)
	{
		ViewAttributeResolver viewAttributeResolver = new ViewAttributeResolver(view, preInitializeViews, pendingBindingAttributes);
		Collection<WidgetViewAttributeProviderAdapter<? extends View>> providers = providersResolver.getCandidateProviders(view);
		
		for (WidgetViewAttributeProviderAdapter<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			WidgetViewAttributeProviderAdapter<View> viewProvider = (WidgetViewAttributeProviderAdapter<View>)provider;
			viewAttributeResolver.resolve(viewProvider);
			
			if (viewAttributeResolver.isDone())
				break;
		}
		
		if (viewAttributeResolver.hasUnresolvedAttributes())
			throw new RuntimeException("Unhandled binding attribute(s): " + viewAttributeResolver.describeUnresolvedAttributes());
		
		return viewAttributeResolver.getResolvedViewAttributes();
	}
	
	public static class ViewAttributes
	{
		final List<ViewAttribute> viewAttributes;
		
		ViewAttributes(List<ViewAttribute> viewAttributes)
		{
			this.viewAttributes = viewAttributes;
		}
		
		public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
		{
			for (ViewAttribute viewAttribute : viewAttributes)
				viewAttribute.bind(presentationModelAdapter, context);
		}
	}
}
