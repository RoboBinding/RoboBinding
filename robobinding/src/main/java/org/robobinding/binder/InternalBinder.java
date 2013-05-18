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

import org.robobinding.BinderImplementor;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.binder.BindingViewInflater.InflatedView;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

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
class InternalBinder implements BinderImplementor {
    private final Context context;
    private final BindingContextCreator bindingContextCreator;
    private ErrorFormatter errorFormatter;
    private final boolean preInitailizeViews;
    private ViewGroup parentView;

    public InternalBinder(Context context, BindingContextCreator bindingContextCreator, ErrorFormatter errorFormatter, boolean preInitailizeViews) {
	this.context = context;
	this.bindingContextCreator = bindingContextCreator;
	this.errorFormatter = errorFormatter;
	this.preInitailizeViews = preInitailizeViews;
    }

    @Override
    public BinderImplementor attachToRoot(ViewGroup parentView) {
	this.parentView = parentView;
	return this;
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel) {
	return inflateAndBind(layoutId, presentationModel, Lists.<PredefinedPendingAttributesForView> newArrayList());
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel,
	    Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	BindingViewInflater viewInflater = createBindingViewInflater(predefinedPendingAttributesForViewGroup);
	InflatedView inflatedView = viewInflater.inflateView(layoutId);

	BindingContext bindingContext = bindingContextCreator.create(presentationModel);
	inflatedView.bindChildViews(bindingContext);
	inflatedView.assertNoErrors(errorFormatter);

	if (preInitailizeViews) {
	    inflatedView.preinitializeViews(bindingContext);
	}

	return inflatedView.getRootView();
    }

    BindingViewInflater createBindingViewInflater(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	BindingViewInflater.Builder viewInflaterBuilder = new BindingViewInflater.Builder(context);
	viewInflaterBuilder.setParentViewToAttach(parentView);
	viewInflaterBuilder.setPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup);
	return viewInflaterBuilder.create();
    }

    @Override
    public View inflateOnly(int layoutId) {
	ViewInflater viewInflater = new NonBindingViewInflater(context, parentView);
	return viewInflater.inflateView(layoutId);
    }

    public static interface BindingContextCreator {
	BindingContext create(Object presentationModel);
    }
}
