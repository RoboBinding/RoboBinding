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
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class InternalBinder implements BinderImplementor {
    private final BindingViewInflater bindingViewInflater;
    private final ErrorFormatter errorFormatter;
    private final BindingContextFactory bindingContextFactory;

    public InternalBinder(BindingViewInflater bindingViewInflater, 
	    BindingContextFactory bindingContextFactory, 
	    ErrorFormatter errorFormatter) {
	this.bindingViewInflater = bindingViewInflater;
	this.bindingContextFactory = bindingContextFactory;
	this.errorFormatter = errorFormatter;
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel) {
	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId);

	return bind(inflatedView, presentationModel);
    }

    private View bind(InflatedView inflatedView, Object presentationModel) {
        BindingContext bindingContext = bindingContextFactory.create(this, presentationModel);
        
        inflatedView.bindChildViews(bindingContext);
        inflatedView.assertNoErrors(errorFormatter);
    
        if (bindingContext.shouldPreInitializeViews()) {
            inflatedView.preinitializeViews(bindingContext);
        }
    
        return inflatedView.getRootView();
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot) {
        InflatedView inflatedView = bindingViewInflater.inflateView(layoutId, attachToRoot);
        
        return bind(inflatedView, presentationModel);
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel,
	    Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId, predefinedPendingAttributesForViewGroup);

	return bind(inflatedView, presentationModel);
    }
}
