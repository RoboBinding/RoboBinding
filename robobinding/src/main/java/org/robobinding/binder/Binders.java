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
	ActivityBinder activityBinder = newBinderFactory().createActivityBinder(activity, true);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    private static BinderFactory newBinderFactory() {
	return new BinderFactoryBuilder().build();
    }

    public static void bindWithoutPreInitializingViews(Activity activity, int layoutId, Object presentationModel) {
	ActivityBinder activityBinder = newBinderFactory().createActivityBinder(activity, false);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static void bind(Dialog dialog, int layoutId, Object presentationModel) {
	DialogBinder dialogBinder = newBinderFactory().createDialogBinder(dialog);
	dialogBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static View bindView(Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = newBinderFactory().createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel);
    }

    public static View attachToRootAndBindView(ViewGroup parentView, Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = newBinderFactory().createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel, parentView);
    }

    private Binders() {
    }
}
