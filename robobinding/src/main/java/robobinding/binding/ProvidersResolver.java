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

import robobinding.binding.widgetattribute.provider.TextViewAttributeProvider;
import robobinding.binding.widgetattribute.provider.ViewAttributeProvider;
import robobinding.binding.widgetattribute.provider.WidgetAttributeProvider;

import android.view.View;
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
	private Queue<WidgetAttributeProvider<? extends View>> candidateProviders;
	private Map<Class<?>, WidgetAttributeProvider<? extends View>> widgetAttributeProviderMap;

	public ProvidersResolver()
	{
		widgetAttributeProviderMap = Maps.newHashMap();
		widgetAttributeProviderMap.put(View.class, new ViewAttributeProvider());
		widgetAttributeProviderMap.put(TextView.class, new TextViewAttributeProvider());
	}
	
	public Queue<WidgetAttributeProvider<? extends View>> getCandidateProviders(View view)
	{
		candidateProviders = Lists.newLinkedList();
		
		if (view instanceof WidgetAttributeProvider)
		{
			@SuppressWarnings("unchecked")
			WidgetAttributeProvider<? extends View> customWidgetAttributeProvider = (WidgetAttributeProvider<? extends View>)view;
			candidateProviders.add(customWidgetAttributeProvider);
		}
				
		processWidgetHierarchy(view.getClass());
		return candidateProviders;
	}

	private void processWidgetHierarchy(Class<?> clazz)
	{
		WidgetAttributeProvider<? extends View> widgetAttributeProvider = lookupProviderForWidgetType(clazz);
		
		if (widgetAttributeProvider != null)
			candidateProviders.add(widgetAttributeProvider);
		
		if (clazz != View.class)
			processWidgetHierarchy(clazz.getSuperclass());
	}

	private WidgetAttributeProvider<? extends View> lookupProviderForWidgetType(Class<?> clazz)
	{
		return widgetAttributeProviderMap .get(clazz);
	}
}
