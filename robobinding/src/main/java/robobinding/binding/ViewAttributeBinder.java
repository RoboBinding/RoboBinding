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

import java.util.List;
import java.util.Map;
import java.util.Queue;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.viewattribute.provider.BindingAttributeProvider;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
class ViewAttributeBinder<T extends View>
{
	static final String ROBOBINDING_NAMESPACE = "http://robobinding.org/robobinding.android";

	private T view;
	private List<BindingAttribute> bindingAttributes;
	private Queue<BindingAttributeProvider<? extends View>> providers;

	private ProvidersResolver providersResolver;
	
	ViewAttributeBinder(T view, AttributeSet attrs)
	{
		this.view = view;
		initializeProviders();
		determineBindingAttributes(attrs);
	}
	
	private void determineBindingAttributes(AttributeSet attrs)
	{
		Map<String, String> pendingBindingAttributes = loadBindingAttributeMap(attrs);
		
		bindingAttributes = Lists.newArrayList();
		
		for (BindingAttributeProvider<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<View> viewProvider = (BindingAttributeProvider<View>)provider;
			List<BindingAttribute> newBindingAttributes = viewProvider.getSupportedBindingAttributes(view, pendingBindingAttributes);
			removeProcessedAttributes(newBindingAttributes, pendingBindingAttributes);
			bindingAttributes.addAll(newBindingAttributes);
		}
		
		if (!pendingBindingAttributes.isEmpty())
			throw new RuntimeException("Unhandled binding attributes");
	}

	private void removeProcessedAttributes(List<BindingAttribute> newBindingAttributes, Map<String, String> pendingBindingAttributes)
	{
		for (BindingAttribute bindingAttribute : newBindingAttributes)
		{
			for (String attributeName : bindingAttribute.getAttributeNames())
				pendingBindingAttributes.remove(attributeName);
		}
	}

	private Map<String, String> loadBindingAttributeMap(AttributeSet attributeSet)
	{
		Map<String, String> pendingBindingAttributes = Maps.newHashMap();
		
		for (int i = 0; i < attributeSet.getAttributeCount(); i++)
		{
			String attributeName = attributeSet.getAttributeName(i);
			String attributeValue = attributeSet.getAttributeValue(ROBOBINDING_NAMESPACE, attributeName);
			
			if (attributeValue != null)
				pendingBindingAttributes.put(attributeName, attributeValue);
		}
		
		return pendingBindingAttributes;
	}

	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		for (BindingAttribute bindingAttribute : bindingAttributes)
			bindingAttribute.bind(presentationModelAdapter, context);
	}

	private void initializeProviders()
	{
		providers = providersResolver.getCandidateProviders(view);
	}

	View getView()
	{
		return view;
	}
}
