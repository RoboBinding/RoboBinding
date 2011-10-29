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

import java.util.Map;
import java.util.Queue;

import robobinding.binding.viewattribute.provider.BindingAttributeProvider;
import robobinding.binding.viewattribute.provider.CheckBoxAttributeProvider;
import robobinding.binding.viewattribute.provider.ListViewAttributeProvider;
import robobinding.binding.viewattribute.provider.TextViewAttributeProvider;
import robobinding.binding.viewattribute.provider.ViewAttributeProvider;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 *
 */
public class ProvidersResolver
{
	private Queue<BindingAttributeProvider<? extends View>> candidateProviders;
	private Map<Class<?>, BindingAttributeProvider<? extends View>> viewAttributeProviderMap;

	public ProvidersResolver()
	{
		viewAttributeProviderMap = Maps.newHashMap();
		viewAttributeProviderMap.put(View.class, new ViewAttributeProvider());
		viewAttributeProviderMap.put(TextView.class, new TextViewAttributeProvider());
		viewAttributeProviderMap.put(ListView.class, new ListViewAttributeProvider());
		viewAttributeProviderMap.put(CheckBox.class, new CheckBoxAttributeProvider());
	}
	
	public Queue<BindingAttributeProvider<? extends View>> getCandidateProviders(View view)
	{
		candidateProviders = Lists.newLinkedList();
		
		if (view instanceof BindingAttributeProvider)
		{
			@SuppressWarnings("unchecked")
			BindingAttributeProvider<? extends View> customViewAttributeProvider = (BindingAttributeProvider<? extends View>)view;
			candidateProviders.add(customViewAttributeProvider);
		}
				
		processViewHierarchy(view.getClass());
		return candidateProviders;
	}

	private void processViewHierarchy(Class<?> clazz)
	{
		BindingAttributeProvider<? extends View> viewAttributeProvider = lookupProviderForViewType(clazz);
		
		if (viewAttributeProvider != null)
			candidateProviders.add(viewAttributeProvider);
		
		if (clazz != View.class)
			processViewHierarchy(clazz.getSuperclass());
	}

	private BindingAttributeProvider<? extends View> lookupProviderForViewType(Class<?> clazz)
	{
		return viewAttributeProviderMap .get(clazz);
	}
}
