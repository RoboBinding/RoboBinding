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
package org.robobinding.binder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.BindingContext;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.binder.BindingAttributeResolver.ViewBindingAttributes;
import org.robobinding.binder.ViewFactory.ViewFactoryListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class ViewInflater implements ViewFactoryListener
{
	private final LayoutInflater layoutInflater;
	private final ViewGroup parentViewToAttach;
	private final List<PredefinedPendingAttributesForView> predefinedViewPendingAttributesGroup;
	BindingAttributeResolver bindingAttributeResolver;
	BindingAttributeParser bindingAttributeParser;
	
	private List<ViewBindingAttributes> childViewBindingAttributesGroup;
	private boolean isInflatingBindingView;

	ViewInflater(Builder builder)
	{
		this.parentViewToAttach = builder.parentViewToAttach;
		this.predefinedViewPendingAttributesGroup = builder.predefinedViewPendingAttributesGroup;

		this.layoutInflater = createLayoutInflaterWithCustomViewFactory(builder.context);
		bindingAttributeResolver = new BindingAttributeResolver();
		bindingAttributeParser = new BindingAttributeParser();
	}

	LayoutInflater createLayoutInflaterWithCustomViewFactory(Context context)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context).cloneInContext(context);
		ViewFactory viewFactory = new ViewFactory(layoutInflater);
		viewFactory.setListener(this);
		return layoutInflater;
	}

	public InflatedView inflateBindingView(int layoutId)
	{
		childViewBindingAttributesGroup = Lists.newArrayList();
		isInflatingBindingView = true;
		
		View rootView = inflate(layoutId);
		addPredefinedPendingAttributesForViewGroup(rootView);
		
		return new InflatedView(rootView, childViewBindingAttributesGroup);
	}

	public View inflateView(int layoutId)
	{
		isInflatingBindingView = false;
		return inflate(layoutId);
	}

	View inflate(int layoutId)
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

	private void addPredefinedPendingAttributesForViewGroup(View rootView)
	{
		for(PredefinedPendingAttributesForView predefinedViewPendingAttributes : predefinedViewPendingAttributesGroup)
		{
			PendingAttributesForView viewPendingAttributes = predefinedViewPendingAttributes.createViewPendingAttributes(rootView);
			resolveAndAddViewBindingAttributes(viewPendingAttributes);
		}
	}

	@Override
	public void onViewCreated(View childView, AttributeSet attrs)
	{
		if(isInflatingBindingView)
		{
			Map<String, String> pendingAttributeMappings = bindingAttributeParser.parse(attrs);
			if(!pendingAttributeMappings.isEmpty())
			{
				PendingAttributesForView viewPendingAttributes = new PendingAttributesForViewImpl(childView, pendingAttributeMappings);
				resolveAndAddViewBindingAttributes(viewPendingAttributes);
			}
		}
	}
	
	private void resolveAndAddViewBindingAttributes(PendingAttributesForView viewPendingAttributes)
	{
		ViewBindingAttributes viewBindingAttributes = bindingAttributeResolver.resolve(viewPendingAttributes);
		childViewBindingAttributesGroup.add(viewBindingAttributes);
	}

	public static class Builder
	{
		private final Context context;
		private ViewGroup parentViewToAttach;
		private List<PredefinedPendingAttributesForView> predefinedViewPendingAttributesGroup;

		public Builder(Context context)
		{
			this.context = context;

			predefinedViewPendingAttributesGroup = Lists.newArrayList();
		}

		public Builder setParentViewToAttach(ViewGroup parentView)
		{
			this.parentViewToAttach = parentView;
			return this;
		}

		public Builder setPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedViewPendingAttributesGroup)
		{
			this.predefinedViewPendingAttributesGroup = Lists.newArrayList(predefinedViewPendingAttributesGroup);
			return this;
		}
		
		public Builder addPredefinedPendingAttributesForView(PredefinedPendingAttributesForView predefinedViewPendingAttributes)
		{
			this.predefinedViewPendingAttributesGroup.add(predefinedViewPendingAttributes);
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
		List<ViewBindingAttributes> childViewBindingAttributesGroup;

		private InflatedView(View rootView, List<ViewBindingAttributes> childViewBindingAttributesGroup)
		{
			this.rootView = rootView;
			this.childViewBindingAttributesGroup = childViewBindingAttributesGroup;
		}

		public View getRootView()
		{
			return rootView;
		}

		public void bindChildViews(BindingContext bindingContext)
		{
			for (ViewBindingAttributes viewBindingAttributes : childViewBindingAttributesGroup)
				viewBindingAttributes.bindTo(bindingContext);
		}
	}
}
