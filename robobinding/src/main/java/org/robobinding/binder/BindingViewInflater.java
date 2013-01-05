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
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;
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
class BindingViewInflater implements ViewFactoryListener
{
	private final List<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup;
	ViewInflater viewInflator;
	BindingAttributeResolver bindingAttributeResolver;
	BindingAttributeParser bindingAttributeParser;
	
	private ViewHierarchyInflationErrorsException errors;
	private List<ResolvedBindingAttributes> childViewBindingAttributesGroup;

	BindingViewInflater(Builder builder)
	{
		this.predefinedPendingAttributesForViewGroup = builder.predefinedPendingAttributesForViewGroup;

		this.viewInflator = createViewInflator(builder);
		bindingAttributeResolver = new BindingAttributeResolver();
		bindingAttributeParser = new BindingAttributeParser();
	}

	private ViewInflater createViewInflator(Builder builder)
	{
		return new NonBindingViewInflater(createLayoutInflaterWithCustomViewFactory(builder.context),
				builder.parentViewToAttach);
	}

	private LayoutInflater createLayoutInflaterWithCustomViewFactory(Context context)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context).cloneInContext(context);
		ViewFactory viewFactory = new ViewFactory(layoutInflater);
		viewFactory.setListener(this);
		return layoutInflater;
	}

	public InflatedView inflateView(int layoutId)
	{
		childViewBindingAttributesGroup = Lists.newArrayList();
		errors = new ViewHierarchyInflationErrorsException();
		
		View rootView = viewInflator.inflateView(layoutId);
		addPredefinedPendingAttributesForViewGroup(rootView);
		
		return new InflatedView(rootView, childViewBindingAttributesGroup, errors);
	}

	private void addPredefinedPendingAttributesForViewGroup(View rootView)
	{
		for(PredefinedPendingAttributesForView predefinedPendingAttributesForView : predefinedPendingAttributesForViewGroup)
		{
			PendingAttributesForView pendingAttributesForView = predefinedPendingAttributesForView.createPendingAttributesForView(rootView);
			resolveAndAddViewBindingAttributes(pendingAttributesForView);
		}
	}

	@Override
	public void onViewCreated(View childView, AttributeSet attrs)
	{
		Map<String, String> pendingAttributeMappings = bindingAttributeParser.parse(attrs);
		if(!pendingAttributeMappings.isEmpty())
		{
			PendingAttributesForView pendingAttributesForView = new PendingAttributesForViewImpl(childView, pendingAttributeMappings);
			resolveAndAddViewBindingAttributes(pendingAttributesForView);
		}
	}
	
	private void resolveAndAddViewBindingAttributes(PendingAttributesForView pendingAttributesForView)
	{
		ViewResolutionResult viewResolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
		viewResolutionResult.addPotentialErrorTo(errors);
		childViewBindingAttributesGroup.add(viewResolutionResult.getResolvedBindingAttributes());
	}

	public static class Builder
	{
		private final Context context;
		private ViewGroup parentViewToAttach;
		private List<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup;

		public Builder(Context context)
		{
			this.context = context;

			predefinedPendingAttributesForViewGroup = Lists.newArrayList();
		}

		public Builder setParentViewToAttach(ViewGroup parentView)
		{
			this.parentViewToAttach = parentView;
			return this;
		}

		public Builder setPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
		{
			this.predefinedPendingAttributesForViewGroup = Lists.newArrayList(predefinedPendingAttributesForViewGroup);
			return this;
		}
		
		public Builder addPredefinedPendingAttributesForView(PredefinedPendingAttributesForView predefinedPendingAttributesForView)
		{
			this.predefinedPendingAttributesForViewGroup.add(predefinedPendingAttributesForView);
			return this;
		}
		
		public BindingViewInflater create()
		{
			return new BindingViewInflater(this);
		}
	}

	static class InflatedView
	{
		private View rootView;
		List<ResolvedBindingAttributes> childViewBindingAttributesGroup;
		private ViewHierarchyInflationErrorsException errors;

		private InflatedView(View rootView, List<ResolvedBindingAttributes> childViewBindingAttributesGroup, ViewHierarchyInflationErrorsException errors)
		{
			this.rootView = rootView;
			this.childViewBindingAttributesGroup = childViewBindingAttributesGroup;
			this.errors = errors;
		}

		public View getRootView()
		{
			return rootView;
		}

		public void bindChildViews(BindingContext bindingContext)
		{
			for (ResolvedBindingAttributes viewBindingAttributes : childViewBindingAttributesGroup)
				errors.addViewBindingError(viewBindingAttributes.bindTo(bindingContext));
		}

		public void assertNoErrors(ErrorFormatter errorFormatter)
		{
			errors.assertNoErrors(errorFormatter);
		}

		public void preinitializeViews(BindingContext bindingContext)
		{
			for (ResolvedBindingAttributes viewBindingAttributes : childViewBindingAttributesGroup)
			{
				viewBindingAttributes.preinitializeView(bindingContext);
			}
		}
	}
}
