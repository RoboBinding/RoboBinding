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

import org.robobinding.BindingContext;
import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.AttributeGroupBindingException;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.ViewAttributeInstantiator;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingAttributeResolver
{
	BindingAttributeProvidersResolver providersResolver;
	ViewAttributeInstantiator viewAttributeInstantiator;
	private ViewBindingAttributes viewBindingAttributes;

	public BindingAttributeResolver()
	{
		this.providersResolver = new BindingAttributeProvidersResolver();
	}

	public ViewResolutionResult resolve(PendingAttributesForView pendingAttributesForView)
	{
		initializeNewResolving(pendingAttributesForView.getView());
		
		resolveByBindingAttributeProviders(pendingAttributesForView);
		
		ViewResolutionErrors errors = pendingAttributesForView.resolveCompleted();
		
		return new ViewResolutionResult(viewBindingAttributes, errors);
	}

	private void initializeNewResolving(View view)
	{
		viewBindingAttributes = new ViewBindingAttributes(view);
		viewAttributeInstantiator = new ViewAttributeInstantiator();
	}

	private void resolveByBindingAttributeProviders(PendingAttributesForView pendingAttributesForView)
	{
		Iterable<BindingAttributeProvider<? extends View>> providers = providersResolver.getCandidateProviders(pendingAttributesForView.getView());
		
		for (BindingAttributeProvider<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<View> bindingAttributeProvider = (BindingAttributeProvider<View>)provider;
			Collection<ViewAttribute> resolvedViewAttributes = resolveByBindingAttributeProvider(pendingAttributesForView, 
					bindingAttributeProvider);
			viewBindingAttributes.addResolvedViewAttributes(resolvedViewAttributes);
			
			if (pendingAttributesForView.isEmpty())
				break;
		}
	}

	Collection<ViewAttribute> resolveByBindingAttributeProvider(PendingAttributesForView pendingAttributesForView,
			BindingAttributeProvider<View> bindingAttributeProvider)
	{
		BindingAttributeMappingsImpl<View> bindingAttributeMappings = bindingAttributeProvider.createBindingAttributeMappings(viewAttributeInstantiator);
		ByBindingAttributeMappingsResolver bindingAttributeMappingsResolver = new ByBindingAttributeMappingsResolver(bindingAttributeMappings);
		Collection<ViewAttribute> resolvedViewAttributes = bindingAttributeMappingsResolver.resolve(pendingAttributesForView);
		return resolvedViewAttributes;
	}

	public static class ViewBindingAttributes
	{
		private View view;
		private final List<ViewAttribute> viewAttributes;
		
		private ViewBindingAttributes(View view)
		{
			this.view = view;
			this.viewAttributes = Lists.newArrayList();
		}
		
		private void addResolvedViewAttributes(Collection<ViewAttribute> viewAttributes)
		{
			this.viewAttributes.addAll(viewAttributes);
		}
		
		public void bindTo(BindingContext bindingContext)
		{
			ViewBindingException viewBindingErrors = new ViewBindingException(view);
			for (ViewAttribute viewAttribute : viewAttributes)
			{
				try
				{
					viewAttribute.bindTo(bindingContext);
				}catch (AttributeGroupBindingException e) 
				{
					viewBindingErrors.addAttributeGroupError(e);
				}catch(AttributeBindingException e)
				{
					viewBindingErrors.addAttributeError(e);
				}
			}
			viewBindingErrors.assertNoErrors();
		}
	}
}
