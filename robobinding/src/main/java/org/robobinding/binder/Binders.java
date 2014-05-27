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
    
    public static View bindView(Context context, View view, Object presentationModel) {
    	InternalViewBinder viewBinder = newBinderFactory().createInternalViewBinder2(context);
    	return viewBinder.justBind(view, presentationModel);
    }

    public static View attachToRootAndBindView(ViewGroup parentView, Context context, int layoutId, Object presentationModel) {
	InternalViewBinder viewBinder = newBinderFactory().createInternalViewBinder(context);
	return viewBinder.inflateAndBind(layoutId, presentationModel, parentView);
    }

    private Binders() {
    }
}
