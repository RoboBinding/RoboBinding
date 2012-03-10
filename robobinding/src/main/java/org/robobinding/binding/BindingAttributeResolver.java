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
package org.robobinding.binding;

import java.util.Collection;
import java.util.List;

import org.robobinding.binder.BindingContext;
import org.robobinding.binder.ViewPendingAttributes;
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
class BindingAttributeResolver
{
	private final BindingAttributeProvidersResolver providersResolver;
	private ViewAttributeInstantiator viewAttributeInstantiator;

	public BindingAttributeResolver()
	{
		this.providersResolver = new BindingAttributeProvidersResolver();
	}

	public ViewBindingAttributes resolve(ViewPendingAttributes viewPendingAttributes)
	{
		ViewBindingAttributes viewBindingAttributes = new ViewBindingAttributes();
		viewAttributeInstantiator = new ViewAttributeInstantiator();
		
		Collection<BindingAttributeProvider<? extends View>> providers = providersResolver.getCandidateProviders(viewPendingAttributes.getView());
		
		for (BindingAttributeProvider<? extends View> provider : providers)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<View> bindingAttributeProvider = (BindingAttributeProvider<View>)provider;
			Collection<ViewAttribute> resolvedViewAttributes = resolveByBindingAttributeProvider(viewPendingAttributes, 
					bindingAttributeProvider);
			viewBindingAttributes.addResolvedViewAttributes(resolvedViewAttributes);
			
			if (viewPendingAttributes.isEmpty())
				break;
		}
		
		viewPendingAttributes.assertAllResolved();
		
		return viewBindingAttributes;
	}

	private Collection<ViewAttribute> resolveByBindingAttributeProvider(ViewPendingAttributes viewPendingAttributes,
			BindingAttributeProvider<View> bindingAttributeProvider)
	{
		BindingAttributeMappingsImpl<View> bindingAttributeMappings = bindingAttributeProvider.createBindingAttributeMappings(viewAttributeInstantiator);
		ByBindingAttributeMappingsResolver bindingAttributeMappingsResolver = new ByBindingAttributeMappingsResolver(bindingAttributeMappings);
		Collection<ViewAttribute> resolvedViewAttributes = bindingAttributeMappingsResolver.resolve(viewPendingAttributes);
		return resolvedViewAttributes;
	}
	
	static class ViewBindingAttributes
	{
		private final List<ViewAttribute> viewAttributes;
		
		private ViewBindingAttributes()
		{
			this.viewAttributes = Lists.newArrayList();
		}
		
		private void addResolvedViewAttributes(Collection<ViewAttribute> viewAttributes)
		{
			this.viewAttributes.addAll(viewAttributes);
		}
		
		public void bindTo(BindingContext bindingContext)
		{
			for (ViewAttribute viewAttribute : viewAttributes)
				viewAttribute.bindTo(bindingContext);
		}
	}
}
