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

import org.robobinding.BinderImplementor;
import org.robobinding.BinderProvider;
import org.robobinding.BindingContext;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;

import android.content.Context;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContextFactory {
    private final Context context;
    private final boolean preInitializeViews;
    private final NonBindingViewInflater nonBindingViewInflater;

    public BindingContextFactory(Context context, boolean preInitializeViews, NonBindingViewInflater nonBindingViewInflater) {
	this.context = context;
	this.preInitializeViews = preInitializeViews;
	this.nonBindingViewInflater = nonBindingViewInflater;
    }

    public BindingContext create(BinderImplementor binderImplementor, Object presentationModel) {
	PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
	BinderProvider binderFactory = new BinderProviderImpl(binderImplementor, nonBindingViewInflater);
	return new BindingContext(binderFactory, context, presentationModelAdapter, preInitializeViews);
    }
}
