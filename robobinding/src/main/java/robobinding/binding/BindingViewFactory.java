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

import java.util.List;

import robobinding.binding.BindingAttributesLoader.ViewBindingAttributes;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingViewFactory implements Factory
{
	private LayoutInflater layoutInflater;
	private List<ViewBindingAttributes> childViewBindingAttributes;
	private final BindingAttributesLoader bindingAttributesLoader;
	
	BindingViewFactory(LayoutInflater layoutInflater, BindingAttributesLoader bindingAttributesLoader)
	{
		this.bindingAttributesLoader = bindingAttributesLoader;
		this.layoutInflater = layoutInflater;
		layoutInflater.setFactory(this);
		childViewBindingAttributes = Lists.newArrayList();
	}

	public View onCreateView(String name, Context context, AttributeSet attrs)
	{
		try
		{
			View view = layoutInflater.createView(name, null, attrs);
			ViewBindingAttributes viewBindingAttributes = bindingAttributesLoader.load(view, attrs);
			childViewBindingAttributes.add(viewBindingAttributes);
			return view;
		} 
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	InflatedView inflateView(int resourceId, Context context)
	{
		childViewBindingAttributes = Lists.newArrayList();
		
		View rootView = layoutInflater.inflate(resourceId, null, false);
		return new InflatedView(rootView, childViewBindingAttributes);
	}
	
	List<ViewBindingAttributes> getChildViewBindingAttributes()
	{
		return childViewBindingAttributes;
	}
	
	static class InflatedView
	{
		private View rootView;
		private List<ViewBindingAttributes> childViewBindingAttributes;
		
		InflatedView(View rootView, List<ViewBindingAttributes> childViewBindingAttributes)
		{
			this.rootView = rootView;
			this.childViewBindingAttributes = childViewBindingAttributes;
		}

		View getRootView()
		{
			return rootView;
		}

		void bindChildViews(PresentationModelAdapter presentationModelAdapter, Context context)
		{
			for (ViewBindingAttributes viewAttributeBinder : childViewBindingAttributes)
				viewAttributeBinder.bind(presentationModelAdapter, context);
		}
	}
}
