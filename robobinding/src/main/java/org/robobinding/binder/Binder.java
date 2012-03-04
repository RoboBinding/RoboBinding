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

import org.robobinding.binders.ActivityBinder;
import org.robobinding.binders.BinderImplementor;
import org.robobinding.binders.DialogBinder;
import org.robobinding.binders.ViewBinder;

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
public class Binder
{
	public static void bind(Activity activity, int layoutId, Object presentationModel)
	{
		BinderImplementor binderImplementor = BinderImplementorFactoryImpl.create(activity, true);
		ActivityBinder activityBinder = new ActivityBinder(activity, binderImplementor);
		activityBinder.bind(layoutId, presentationModel);
	}
	
	public static void bindWithoutPreInitializingViews(Activity activity, int layoutId, Object presentationModel)
	{
		BinderImplementor binderImplementor = BinderImplementorFactoryImpl.create(activity, false);
		ActivityBinder activityBinder = new ActivityBinder(activity, binderImplementor);
		activityBinder.bind(layoutId, presentationModel);
	}
	
	public static void bind(Dialog dialog, int layoutId, Object presentationModel)
	{
		BinderImplementor binderImplementor = BinderImplementorFactoryImpl.create(dialog.getContext(), true);
		DialogBinder dialogBinder = new DialogBinder(dialog, binderImplementor);
		dialogBinder.bind(layoutId, presentationModel);
	}
	
	public static View bindView(Context context, int layoutId, Object presentationModel)
	{
		BinderImplementor binderImplementor = BinderImplementorFactoryImpl.create(context, true);
		ViewBinder viewBinder = new ViewBinder(binderImplementor);
		return viewBinder.bind(layoutId, presentationModel);
	}
	
	public static View attachToRootAndBindView(ViewGroup parentView, Context context, int layoutId, Object presentationModel)
	{
		BinderImplementor binderImplementor = BinderImplementorFactoryImpl.create(context, true);
		ViewBinder viewBinder = new ViewBinder(binderImplementor);
		viewBinder.attachToRoot(parentView);
		return viewBinder.bind(layoutId, presentationModel);
	}
}
