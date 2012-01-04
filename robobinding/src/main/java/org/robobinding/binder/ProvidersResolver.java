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
import org.robobinding.viewattribute.WidgetViewAttributeProvider;
import org.robobinding.viewattribute.adapterview.AdapterViewAttributeProvider;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonAttributeProvider;
import org.robobinding.viewattribute.imageview.ImageViewAttributeProvider;
import org.robobinding.viewattribute.progressbar.ProgressBarAttributeProvider;
import org.robobinding.viewattribute.ratingbar.RatingBarAttributeProvider;
import org.robobinding.viewattribute.seekbar.SeekBarAttributeProvider;
import org.robobinding.viewattribute.textview.TextViewAttributeProvider;
import org.robobinding.viewattribute.view.ViewAttributeProvider;

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
public class ProvidersResolver
{
	private final Map<Class<?>, WidgetViewAttributeProviderAdapter<? extends View>> widgetAttributeProviderMap;

	public ProvidersResolver()
	{
		widgetAttributeProviderMap = Maps.newHashMap();
		widgetAttributeProviderMap.put(View.class, adapt(new ViewAttributeProvider()));
		widgetAttributeProviderMap.put(TextView.class, adapt(new TextViewAttributeProvider()));
		widgetAttributeProviderMap.put(AdapterView.class, adapt(new AdapterViewAttributeProvider()));
		widgetAttributeProviderMap.put(CompoundButton.class, adapt(new CompoundButtonAttributeProvider()));
		widgetAttributeProviderMap.put(ImageView.class, adapt(new ImageViewAttributeProvider()));
		widgetAttributeProviderMap.put(ProgressBar.class, adapt(new ProgressBarAttributeProvider()));
		widgetAttributeProviderMap.put(SeekBar.class, adapt(new SeekBarAttributeProvider()));
		widgetAttributeProviderMap.put(RatingBar.class, adapt(new RatingBarAttributeProvider()));
	}
	
	private <T extends View> WidgetViewAttributeProviderAdapter<T> adapt(WidgetViewAttributeProvider<T> provider)
	{
		return new WidgetViewAttributeProviderAdapter<T>(provider);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Queue<WidgetViewAttributeProviderAdapter<? extends View>> getCandidateProviders(View view)
	{
		Queue<WidgetViewAttributeProviderAdapter<? extends View>> candidateProviders = Lists.newLinkedList();
		
		if (CustomWidgetUtils.isCustomWidget(view))
		{
			candidateProviders.add(CustomWidgetUtils.adapt((BindableView)view));
		}
				
		processViewHierarchy(view.getClass(), candidateProviders);
		return candidateProviders;
	}

	private void processViewHierarchy(Class<?> clazz, Queue<WidgetViewAttributeProviderAdapter<? extends View>> candidateProviders)
	{
		WidgetViewAttributeProviderAdapter<? extends View> widgetAttributeProvider = lookupProviderForViewType(clazz);
		
		if (widgetAttributeProvider != null)
			candidateProviders.add(widgetAttributeProvider);
		
		if (clazz != View.class)
			processViewHierarchy(clazz.getSuperclass(), candidateProviders);
	}

	private WidgetViewAttributeProviderAdapter<? extends View> lookupProviderForViewType(Class<?> clazz)
	{
		return widgetAttributeProviderMap.get(clazz);
	}
}