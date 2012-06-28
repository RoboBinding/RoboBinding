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

<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
import org.robobinding.BindingContext;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;
=======
import org.robobinding.binder.BindingViewInflationErrors.ErrorFormatter;
>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
import org.robobinding.binder.ViewFactory.ViewFactoryListener;

import android.content.Context;
=======
import org.robobinding.binder.BindingContext;
import org.robobinding.binder.PredefinedViewPendingAttributes;
import org.robobinding.binder.ViewPendingAttributes;
import org.robobinding.binder.ViewPendingAttributesImpl;
import org.robobinding.binding.BindingAttributeResolver.ViewBindingAttributes;
import org.robobinding.binding.ViewFactory.ViewCreationListener;

>>>>>>> Apply PredefinedViewPendingAttributes and BindingContext ideas.:robobinding/src/main/java/org/robobinding/binding/ViewInflater.java
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
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
class BindingViewInflater implements ViewFactoryListener
{
=======
class BindingViewInflater implements ViewFactoryListener
{
	private final ViewInflator viewInflator;
>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
	private final List<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup;
	ViewInflater viewInflator;
	BindingAttributeResolver bindingAttributeResolver;
	BindingAttributeParser bindingAttributeParser;
	
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
	private ViewHierarchyInflationErrorsException errors;
	private List<ResolvedBindingAttributes> childViewBindingAttributesGroup;

	BindingViewInflater(Builder builder)
=======
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
>>>>>>> Apply PredefinedViewPendingAttributes and BindingContext ideas.:robobinding/src/main/java/org/robobinding/binding/ViewInflater.java
	{
		this.predefinedPendingAttributesForViewGroup = builder.predefinedPendingAttributesForViewGroup;

<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
=======
	private BindingViewInflationErrors errors;
	private List<ResolvedBindingAttributes> childViewBindingAttributesGroup;

	BindingViewInflater(Builder builder)
	{
		this.predefinedPendingAttributesForViewGroup = builder.predefinedPendingAttributesForViewGroup;

>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
		this.viewInflator = createViewInflator(builder);
		bindingAttributeResolver = new BindingAttributeResolver();
		bindingAttributeParser = new BindingAttributeParser();
	}

<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
	private ViewInflater createViewInflator(Builder builder)
=======
	ViewInflator createViewInflator(Builder builder)
	{
		return new ViewInflator(createLayoutInflaterWithCustomViewFactory(builder.context),
				builder.parentViewToAttach);
	}

	LayoutInflater createLayoutInflaterWithCustomViewFactory(Context context)
>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
	{
		return new NonBindingViewInflater(createLayoutInflaterWithCustomViewFactory(builder.context),
				builder.parentViewToAttach);
	}

	private LayoutInflater createLayoutInflaterWithCustomViewFactory(Context context)
=======
		ViewFactory viewFactory = new ViewFactory(layoutInflater);
		viewFactory.setListener(this);
		bindingAttributeResolver = new BindingAttributeResolver();
		bindingAttributesParser = new BindingAttributeParser();
	}

	public InflatedView inflateView(int layoutId)
	{
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
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
>>>>>>> Apply PredefinedViewPendingAttributes and BindingContext ideas.:robobinding/src/main/java/org/robobinding/binding/ViewInflater.java
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
		
=======
		childViewBindingAttributesGroup = Lists.newArrayList();
		errors = new BindingViewInflationErrors();
		
		View rootView = viewInflator.inflateView(layoutId);
		addPredefinedPendingAttributesForViewGroup(rootView);
		
>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
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
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
		Map<String, String> pendingAttributeMappings = bindingAttributeParser.parse(attrs);
		if(!pendingAttributeMappings.isEmpty())
		{
			PendingAttributesForView pendingAttributesForView = new PendingAttributesForViewImpl(childView, pendingAttributeMappings);
			resolveAndAddViewBindingAttributes(pendingAttributesForView);
=======
		if(isInflatingBindingView)
		{
			Map<String, String> pendingAttributeMappings = bindingAttributesParser.parse(attrs);
			ViewPendingAttributes viewPendingAttributes = new ViewPendingAttributesImpl(childView, pendingAttributeMappings);
			resolveAndAddViewBindingAttributes(viewPendingAttributes);
>>>>>>> Apply PredefinedViewPendingAttributes and BindingContext ideas.:robobinding/src/main/java/org/robobinding/binding/ViewInflater.java
=======
		Map<String, String> pendingAttributeMappings = bindingAttributeParser.parse(attrs);
		if(!pendingAttributeMappings.isEmpty())
		{
			PendingAttributesForView pendingAttributesForView = new PendingAttributesForViewImpl(childView, pendingAttributeMappings);
			resolveAndAddViewBindingAttributes(pendingAttributesForView);
>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
		}
	}
	
	private void resolveAndAddViewBindingAttributes(PendingAttributesForView pendingAttributesForView)
	{
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
		ViewResolutionResult viewResolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
		viewResolutionResult.addPotentialErrorTo(errors);
		childViewBindingAttributesGroup.add(viewResolutionResult.getResolvedBindingAttributes());
=======
		ViewBindingAttributes viewBindingAttributes = bindingAttributeResolver.resolve(viewPendingAttributes);
		childViewBindingAttributes.add(viewBindingAttributes);
>>>>>>> Apply PredefinedViewPendingAttributes and BindingContext ideas.:robobinding/src/main/java/org/robobinding/binding/ViewInflater.java
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
<<<<<<< HEAD:robobinding/src/main/java/org/robobinding/binder/ViewInflater.java
		private ViewHierarchyInflationErrorsException errors;

		private InflatedView(View rootView, List<ResolvedBindingAttributes> childViewBindingAttributesGroup, ViewHierarchyInflationErrorsException errors)
=======
		private BindingViewInflationErrors errors;

		private InflatedView(View rootView, List<ResolvedBindingAttributes> childViewBindingAttributesGroup, BindingViewInflationErrors errors)
>>>>>>> Added a sample test.:robobinding/src/main/java/org/robobinding/binder/BindingViewInflater.java
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
	}
}
