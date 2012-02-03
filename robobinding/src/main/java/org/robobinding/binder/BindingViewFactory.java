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

import java.util.List;

import org.robobinding.binder.BindingAttributeProcessor.ViewAttributes;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import android.view.ViewGroup;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingViewFactory implements Factory
{
	private final LayoutInflater layoutInflater;
	private final BindingAttributeProcessor bindingAttributeProcessor;
	private final ViewNameResolver viewNameResolver;
	
	private List<ViewAttributes> childViewBindingAttributes = Lists.newArrayList();
	
	BindingViewFactory(LayoutInflater layoutInflater, BindingAttributeProcessor bindingAttributeProcessor)
	{
		this.layoutInflater = layoutInflater;
		this.bindingAttributeProcessor = bindingAttributeProcessor;
		this.viewNameResolver = new ViewNameResolver();
		layoutInflater.setFactory(this);
	}

	public View onCreateView(String name, Context context, AttributeSet attrs)
	{
		try
		{
			String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);
			
			View view = layoutInflater.createView(viewFullName, null, attrs);
			ViewAttributes viewBindingAttributes = bindingAttributeProcessor.read(view, attrs);
			childViewBindingAttributes.add(viewBindingAttributes);
			return view;
		} 
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
 
	InflatedView inflateView(int resourceId)
	{
		childViewBindingAttributes = Lists.newArrayList();
		View rootView = layoutInflater.inflate(resourceId, null);
		return new InflatedView(rootView, childViewBindingAttributes);
	}
	
	InflatedView inflateViewAndAttachToRoot(int resourceId, ViewGroup viewGroup)
	{
		childViewBindingAttributes = Lists.newArrayList();
		View rootView = layoutInflater.inflate(resourceId, viewGroup, true);
		return new InflatedView(rootView, childViewBindingAttributes);
	}
	
	List<ViewAttributes> getChildViewBindingAttributes()
	{
		return childViewBindingAttributes;
	}
	
	public static class InflatedView
	{
		private View rootView;
		private List<ViewAttributes> childViewBindingAttributes;
		
		InflatedView(View rootView, List<ViewAttributes> childViewBindingAttributes)
		{
			this.rootView = rootView;
			this.childViewBindingAttributes = childViewBindingAttributes;
		}

		public View getRootView()
		{
			return rootView;
		}

		void bindChildViews(PresentationModelAdapter presentationModelAdapter, Context context)
		{
			for (ViewAttributes viewAttributeBinder : childViewBindingAttributes)
				viewAttributeBinder.bind(presentationModelAdapter, context);
		}
	}
}
