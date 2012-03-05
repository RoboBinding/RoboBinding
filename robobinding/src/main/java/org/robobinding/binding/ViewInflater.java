/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
import java.util.Map;

import org.robobinding.binder.BindingContext;
import org.robobinding.binder.PredefinedViewPendingAttributes;
import org.robobinding.binder.ViewPendingAttributes;
import org.robobinding.binder.ViewPendingAttributesImpl;
import org.robobinding.binding.BindingAttributeResolver.ViewBindingAttributes;
import org.robobinding.binding.ViewFactory.ViewCreationListener;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ViewInflater implements ViewCreationListener
{
	private final LayoutInflater layoutInflater;
	private final ViewGroup parentViewToAttach;
	private final List<PredefinedViewPendingAttributes> predefinedViewPendingAttributesGroup;
	private final BindingAttributeResolver bindingAttributeResolver;
	private final BindingAttributeParser bindingAttributesParser;
	
	private List<ViewBindingAttributes> childViewBindingAttributes;
	private boolean isInflatingBindingView;

	private ViewInflater(Builder builder)
	{
		this.layoutInflater = builder.layoutInflater;
		this.parentViewToAttach = builder.parentViewToAttach;
		this.predefinedViewPendingAttributesGroup = builder.predefinedViewPendingAttributesGroup;

		ViewFactory viewFactory = new ViewFactory(layoutInflater);
		viewFactory.setListener(this);
		bindingAttributeResolver = new BindingAttributeResolver();
		bindingAttributesParser = new BindingAttributeParser();
	}

	public InflatedView inflateBindingView(int layoutId)
	{
		childViewBindingAttributes = Lists.newArrayList();
		isInflatingBindingView = true;
		
		View rootView = inflate(layoutId);
		addPredefinedViewPendingAttributesGroup(rootView);
		
		return new InflatedView(rootView, childViewBindingAttributes);
	}

	public View inflateView(int layoutId)
	{
		isInflatingBindingView = false;
		return inflate(layoutId);
	}

	private View inflate(int layoutId)
	{
		if(shouldAttachToParentView())
		{
			return layoutInflater.inflate(layoutId, parentViewToAttach, true);
		}else
		{
			return layoutInflater.inflate(layoutId, null);
		}
	}

	private boolean shouldAttachToParentView()
	{
		return parentViewToAttach != null;
	}

	private void addPredefinedViewPendingAttributesGroup(View rootView)
	{
		for(PredefinedViewPendingAttributes predefinedViewPendingAttributes : predefinedViewPendingAttributesGroup)
		{
			ViewPendingAttributes viewPendingAttributes = predefinedViewPendingAttributes.createViewPendingAttributes(rootView);
			resolveAndAddViewBindingAttributes(viewPendingAttributes);
		}
	}

	@Override
	public void onViewCreated(View childView, AttributeSet attrs)
	{
		if(isInflatingBindingView)
		{
			Map<String, String> pendingAttributeMappings = bindingAttributesParser.parse(attrs);
			ViewPendingAttributes viewPendingAttributes = new ViewPendingAttributesImpl(childView, pendingAttributeMappings);
			resolveAndAddViewBindingAttributes(viewPendingAttributes);
		}
	}
	
	private void resolveAndAddViewBindingAttributes(ViewPendingAttributes viewPendingAttributes)
	{
		ViewBindingAttributes viewBindingAttributes = bindingAttributeResolver.resolve(viewPendingAttributes);
		childViewBindingAttributes.add(viewBindingAttributes);
	}

	public static class Builder
	{
		private final LayoutInflater layoutInflater;
		private ViewGroup parentViewToAttach;
		private List<PredefinedViewPendingAttributes> predefinedViewPendingAttributesGroup;

		public Builder(LayoutInflater layoutInflater)
		{
			this.layoutInflater = layoutInflater;

			predefinedViewPendingAttributesGroup = Lists.newArrayList();
		}

		public Builder setParentViewToAttach(ViewGroup parentView)
		{
			this.parentViewToAttach = parentView;
			return this;
		}

		public Builder setPredefinedViewPendingAttributesGroup(Collection<PredefinedViewPendingAttributes> predefinedViewPendingAttributesGroup)
		{
			this.predefinedViewPendingAttributesGroup = Lists.newArrayList(predefinedViewPendingAttributesGroup);
			return this;
		}
		
		public ViewInflater create()
		{
			return new ViewInflater(this);
		}
	}

	static class InflatedView
	{
		private View rootView;
		private List<ViewBindingAttributes> childViewBindingAttributes;

		private InflatedView(View rootView, List<ViewBindingAttributes> childViewBindingAttributes)
		{
			this.rootView = rootView;
			this.childViewBindingAttributes = childViewBindingAttributes;
		}

		public View getRootView()
		{
			return rootView;
		}

		public void bindChildViews(BindingContext context)
		{
			for (ViewBindingAttributes viewBindingAttributes : childViewBindingAttributes)
				viewBindingAttributes.bindTo(context);
		}
	}
}
