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

import org.robobinding.ActivityBinder;
import org.robobinding.BinderImplementor;
import org.robobinding.DialogBinder;
import org.robobinding.InternalViewBinder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class Binders {
    public static void bind(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = createActivityBinder(activity, true);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    private static ActivityBinder createActivityBinder(Activity activity, boolean withPreInitializingViews) {
	BinderImplementor binderImplementor = createBinderImplementor(activity, withPreInitializingViews);
	return new ActivityBinder(activity, binderImplementor);
    }

    private static BinderImplementor createBinderImplementor(Context context, boolean withPreInitializingViews) {
	NonBindingViewInflater nonBindingViewInflater = NonBindingViewInflater.create(context);
	ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
		new BindingAttributeProvidersResolver());
	BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver,
		new BindingAttributeParser());
	nonBindingViewInflater.installViewFactory(new ViewFactoryInstaller(bindingViewInflater));
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews);
	BinderImplementor binderImplementor = new InternalBinder(bindingViewInflater, bindingContextFactory, new PlainTextErrorFormatter());
	return binderImplementor;
    }
    
    public static void bindWithoutPreInitializingViews(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = createActivityBinder(activity, false);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static void bind(Dialog dialog, int layoutId, Object presentationModel) {
	DialogBinder dialogBinder = createDialogBinder(dialog);
	dialogBinder.inflateAndBind(layoutId, presentationModel);
    }
    
    private static DialogBinder createDialogBinder(Dialog dialog) {
	BinderImplementor binderImplementor = createBinderImplementor(dialog.getContext(), true);
	return new DialogBinder(dialog, binderImplementor);
    }

    public static View bindView(Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel);
    }

    private static InternalViewBinder createInternalViewBinder(Context context)
    {
	BinderImplementor binderImplementor = createBinderImplementor(context, true);
	return new InternalViewBinder(binderImplementor, NonBindingViewInflater.create(context));
    }
    
    public static View attachToRootAndBindView(ViewGroup parentView, Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel, parentView);
    }
}
