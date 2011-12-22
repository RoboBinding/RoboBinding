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

import java.util.Map;
import java.util.Queue;

import org.robobinding.customwidget.CustomWidgetUtils;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeProvider;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeProvider;
import org.robobinding.viewattribute.imageview.ImageViewAttributeProvider;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeProvider;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeProvider;
import org.robobinding.viewattribute.textview.TextViewAttributeProvider;
import org.robobinding.viewattribute.view.ViewAttributeProvider;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
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
		viewAttributeProviderMap.put(CompoundButton.class, new CompoundButtonAttributeProvider());
		viewAttributeProviderMap.put(ImageView.class, new ImageViewAttributeProvider());
		viewAttributeProviderMap.put(ProgressBar.class, new ProgressBarAttributeProvider());
		viewAttributeProviderMap.put(SeekBar.class, new SeekBarAttributeProvider());
	}
	
	public Queue<BindingAttributeProvider<? extends View>> getCandidateProviders(View view)
	{
		Queue<BindingAttributeProvider<? extends View>> candidateProviders = Lists.newLinkedList();
		
		if (CustomWidgetUtils.isCustomWidget(view))
		{
			candidateProviders.add(CustomWidgetUtils.getBindingAttributeProvider(view));
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