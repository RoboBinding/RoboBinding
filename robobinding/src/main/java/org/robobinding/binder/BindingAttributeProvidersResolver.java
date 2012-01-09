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

import org.robobinding.customwidget.BindableView;
import org.robobinding.customwidget.CustomWidgetUtils;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMapperAdapter;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeMapper;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeMapper;
import org.robobinding.viewattribute.imageview.ImageViewAttributeMapper;
import org.robobinding.viewattribute.listview.ListViewAttributeMapper;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarAttributeMapper;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeMapper;
import org.robobinding.viewattribute.textview.TextViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewAttributeMapper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class BindingAttributeProvidersResolver
{
	private final Map<Class<?>, BindingAttributeProvider<? extends View>> bindingAttributeProvidersMap;

	public BindingAttributeProvidersResolver()
	{
		bindingAttributeProvidersMap = Maps.newHashMap();
		bindingAttributeProvidersMap.put(View.class, adapt(new ViewAttributeMapper()));
		bindingAttributeProvidersMap.put(TextView.class, adapt(new TextViewAttributeMapper()));
		bindingAttributeProvidersMap.put(AdapterView.class, adapt(new AdapterViewAttributeMapper()));
		bindingAttributeProvidersMap.put(CompoundButton.class, adapt(new CompoundButtonAttributeMapper()));
		bindingAttributeProvidersMap.put(ImageView.class, adapt(new ImageViewAttributeMapper()));
		bindingAttributeProvidersMap.put(ProgressBar.class, adapt(new ProgressBarAttributeMapper()));
		bindingAttributeProvidersMap.put(SeekBar.class, adapt(new SeekBarAttributeMapper()));
		bindingAttributeProvidersMap.put(RatingBar.class, adapt(new RatingBarAttributeMapper()));
		bindingAttributeProvidersMap.put(ListView.class, adapt(new ListViewAttributeMapper()));
	}
	
	private <T extends View> BindingAttributeProvider<T> adapt(BindingAttributeMapper<T> mapper)
	{
		return new BindingAttributeMapperAdapter<T>(mapper);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Queue<BindingAttributeProvider<? extends View>> getCandidateProviders(View view)
	{
		Queue<BindingAttributeProvider<? extends View>> candidateProviders = Lists.newLinkedList();
		
		if (CustomWidgetUtils.isCustomWidget(view))
		{
			candidateProviders.add(CustomWidgetUtils.adapt((BindableView)view));
		}
				
		processViewHierarchy(view.getClass(), candidateProviders);
		return candidateProviders;
	}

	private void processViewHierarchy(Class<?> clazz, Queue<BindingAttributeProvider<? extends View>> candidateProviders)
	{
		BindingAttributeProvider<? extends View> bindingAttributeProvider = lookupProviderForViewType(clazz);
		
		if (bindingAttributeProvider != null)
			candidateProviders.add(bindingAttributeProvider);
		
		if (clazz != View.class)
			processViewHierarchy(clazz.getSuperclass(), candidateProviders);
	}

	private BindingAttributeProvider<? extends View> lookupProviderForViewType(Class<?> clazz)
	{
		return bindingAttributeProvidersMap.get(clazz);
	}
}