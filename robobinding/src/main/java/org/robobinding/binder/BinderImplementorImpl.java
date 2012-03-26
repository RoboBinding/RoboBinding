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

import java.util.Collection;
import java.util.List;

import org.robobinding.BinderImplementor;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.binder.ViewInflater.InflatedView;

import android.content.Context;
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
class BinderImplementorImpl implements BinderImplementor
{
	private final Context context;
	private final BindingContextCreator bindingContextCreator;
	private ViewGroup parentView;
	private List<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup;
	
	public BinderImplementorImpl(Context context, BindingContextCreator bindingContextCreator)
	{
		this.context = context;
		this.bindingContextCreator = bindingContextCreator;
		predefinedPendingAttributesForViewGroup = Lists.newArrayList();
	}
	
	@Override
	public BinderImplementor attachToRoot(ViewGroup parentView)
	{
		this.parentView = parentView;
		return this;
	}
	
	public BinderImplementor setPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
	{
		this.predefinedPendingAttributesForViewGroup = Lists.newArrayList(predefinedPendingAttributesForViewGroup);
		return this;
	}
	
	@Override
	public View inflateAndBind(int layoutId, Object presentationModel)
	{
		ViewInflater viewInflater = createViewInflater();
		InflatedView inflatedView = viewInflater.inflateBindingView(layoutId);
		
		BindingContext bindingContext = bindingContextCreator.create(presentationModel);
		inflatedView.bindChildViews(bindingContext);
		
		return inflatedView.getRootView();
	}

	@Override
	public View inflateOnly(int layoutId)
	{
		ViewInflater viewInflater = createViewInflater();
		return viewInflater.inflateView(layoutId);
	}

	ViewInflater createViewInflater()
	{
		ViewInflater.Builder viewInflaterBuilder = new ViewInflater.Builder(context);
		viewInflaterBuilder.setParentViewToAttach(parentView);
		viewInflaterBuilder.setPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup);
		return viewInflaterBuilder.create();
	}

	public static interface BindingContextCreator
	{
		BindingContext create(Object presentationModel);
	}
}
