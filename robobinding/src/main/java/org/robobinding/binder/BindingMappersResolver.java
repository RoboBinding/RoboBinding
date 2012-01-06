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
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeMapper;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeMapper;
import org.robobinding.viewattribute.imageview.ImageViewAttributeMapper;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeMapper;
import org.robobinding.viewattribute.ratingbar.RatingBarAttributeMapper;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeMapper;
import org.robobinding.viewattribute.textview.TextViewAttributeMapper;
import org.robobinding.viewattribute.view.ViewAttributeMapper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
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
public class BindingMappersResolver
{
	private final Map<Class<?>, BindingAttributeMapperAdapter<? extends View>> bindingAttributeMappersMap;

	public BindingMappersResolver()
	{
		bindingAttributeMappersMap = Maps.newHashMap();
		bindingAttributeMappersMap.put(View.class, adapt(new ViewAttributeMapper()));
		bindingAttributeMappersMap.put(TextView.class, adapt(new TextViewAttributeMapper()));
		bindingAttributeMappersMap.put(AdapterView.class, adapt(new AdapterViewAttributeMapper()));
		bindingAttributeMappersMap.put(CompoundButton.class, adapt(new CompoundButtonAttributeMapper()));
		bindingAttributeMappersMap.put(ImageView.class, adapt(new ImageViewAttributeMapper()));
		bindingAttributeMappersMap.put(ProgressBar.class, adapt(new ProgressBarAttributeMapper()));
		bindingAttributeMappersMap.put(SeekBar.class, adapt(new SeekBarAttributeMapper()));
		bindingAttributeMappersMap.put(RatingBar.class, adapt(new RatingBarAttributeMapper()));
	}
	
	private <T extends View> BindingAttributeMapperAdapter<T> adapt(BindingAttributeMapper<T> mapper)
	{
		return new BindingAttributeMapperAdapterImpl<T>(mapper);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Queue<BindingAttributeMapperAdapter<? extends View>> getCandidateMappers(View view)
	{
		Queue<BindingAttributeMapperAdapter<? extends View>> candidateMappers = Lists.newLinkedList();
		
		if (CustomWidgetUtils.isCustomWidget(view))
		{
			candidateMappers.add(CustomWidgetUtils.adapt((BindableView)view));
		}
				
		processViewHierarchy(view.getClass(), candidateMappers);
		return candidateMappers;
	}

	private void processViewHierarchy(Class<?> clazz, Queue<BindingAttributeMapperAdapter<? extends View>> candidateProviders)
	{
		BindingAttributeMapperAdapter<? extends View> widgetAttributeProvider = lookupProviderForViewType(clazz);
		
		if (widgetAttributeProvider != null)
			candidateProviders.add(widgetAttributeProvider);
		
		if (clazz != View.class)
			processViewHierarchy(clazz.getSuperclass(), candidateProviders);
	}

	private BindingAttributeMapperAdapter<? extends View> lookupProviderForViewType(Class<?> clazz)
	{
		return bindingAttributeMappersMap.get(clazz);
	}
}