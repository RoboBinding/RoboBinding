package org.robobinding.binder;

import org.robobinding.ViewBinder;

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

    public static View inflateAndBind(Context context, int layoutId, Object presentationModel) {
	return inflateAndBind(context, layoutId, presentationModel, true);
    }
    
    private static View inflateAndBind(Context context, int layoutId, Object presentationModel, boolean withPreInitializingViews) {
	return inflateAndBind(context, layoutId, presentationModel, null, withPreInitializingViews);
    }

    private static View inflateAndBind(Context context, int layoutId, Object presentationModel, ViewGroup attachToRoot, boolean withPreInitializingViews) {
        ViewBinder viewBinder = newBinderFactory().createViewBinder(context, withPreInitializingViews);
        if (attachToRoot == null) {
            return viewBinder.inflateAndBind(layoutId, presentationModel);
        } else {
            return viewBinder.inflateAndBind(layoutId, presentationModel, attachToRoot);
        }
    }

    private static BinderFactory newBinderFactory() {
        return new BinderFactoryBuilder().build();
    }

    public static View inflateAndBindWithoutPreInitializingViews(Context context, int layoutId, Object presentationModel) {
	return inflateAndBind(context, layoutId, presentationModel, false);
    }

    public static View inflateAndBind(Context context, int layoutId, Object presentationModel, ViewGroup attachToRoot) {
	return inflateAndBind(context, layoutId, presentationModel, attachToRoot, true);
    }

    public static View inflateAndBindWithoutPreInitializingViews(Context context, int layoutId, Object presentationModel, ViewGroup attachToRoot) {
	return inflateAndBind(context, layoutId, presentationModel, attachToRoot, false);
    }

    private Binders() {
    }
}
