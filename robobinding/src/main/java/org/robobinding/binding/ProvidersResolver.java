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

import java.util.Map;
import java.util.Queue;

import org.robobinding.binding.viewattribute.provider.AdapterViewAttributeProvider;
import org.robobinding.binding.viewattribute.provider.BindingAttributeProvider;
import org.robobinding.binding.viewattribute.provider.CheckBoxAttributeProvider;
import org.robobinding.binding.viewattribute.provider.TextViewAttributeProvider;
import org.robobinding.binding.viewattribute.provider.ViewAttributeProvider;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Maps;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class ProvidersResolver
{
	private final Map<Class<?>, BindingAttributeProvider<? extends View>> viewAttributeProviderMap;

	public ProvidersResolver()
	{
		viewAttributeProviderMap = Maps.newHashMap();
		viewAttributeProviderMap.put(View.class, new ViewAttributeProvider());
		viewAttributeProviderMap.put(TextView.class, new TextViewAttributeProvider());
		viewAttributeProviderMap.put(AdapterView.class, new AdapterViewAttributeProvider());
		viewAttributeProviderMap.put(CheckBox.class, new CheckBoxAttributeProvider());
	}
	
	public Queue<BindingAttributeProvider<? extends View>> getCandidateProviders(View view)
	{
		Queue<BindingAttributeProvider<? extends View>> candidateProviders = Lists.newLinkedList();
		
		if (view instanceof BindingAttributeProvider)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<? extends View> customViewAttributeProvider = (BindingAttributeProvider<? extends View>)view;
			candidateProviders.add(customViewAttributeProvider);
		}
				
		processViewHierarchy(view.getClass(), candidateProviders);
		return candidateProviders;
	}

	private void processViewHierarchy(Class<?> clazz, Queue<BindingAttributeProvider<? extends View>> candidateProviders)
	{
		BindingAttributeProvider<? extends View> viewAttributeProvider = lookupProviderForViewType(clazz);
		
		if (viewAttributeProvider != null)
			candidateProviders.add(viewAttributeProvider);
		
		if (clazz != View.class)
			processViewHierarchy(clazz.getSuperclass(), candidateProviders);
	}

	private BindingAttributeProvider<? extends View> lookupProviderForViewType(Class<?> clazz)
	{
		return viewAttributeProviderMap.get(clazz);
	}
}
