package org.robobinding.binder;

import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Binding entry utility class. In real-world application development, consider
 * using {@link BinderFactoryBuilder} instead, so that we can reuse
 * {@link BinderFactory} instance.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class Binders {

	/**
	 * @see ViewBinder#inflateAndBind(int, Object)
	 */
	public static View inflateAndBind(Context context, int layoutId, Object presentationModel) {
		return inflateAndBind(context, layoutId, presentationModel, true);
	}

	public static View inflateAndBind(Context context, int layoutId, Object presentationModel, ViewGroup root, boolean attachToRoot) {
		return inflateAndBind(context, layoutId, presentationModel, true, root, attachToRoot);
	}

	private static View inflateAndBind(Context context, int layoutId, Object presentationModel, boolean withPreInitializingViews) {
		return inflateAndBind(context, layoutId, presentationModel, withPreInitializingViews, null, false);
	}

	private static View inflateAndBind(Context context, int layoutId, Object presentationModel, boolean withPreInitializingViews, 
			ViewGroup root, boolean attachToRoot) {
		ViewBinder viewBinder = newBinderFactory().createViewBinder(context, withPreInitializingViews);
		if (root == null) {
			return viewBinder.inflateAndBind(layoutId, presentationModel);
		} else if(attachToRoot){
			return viewBinder.inflateAndBind(layoutId, presentationModel, root);
		} else {
			return viewBinder.inflateAndBindWithoutAttachingToRoot(layoutId, presentationModel, root);
		}
	}

	private static BinderFactory newBinderFactory() {
		return new BinderFactoryBuilder().build();
	}

	/**
	 * @see ViewBinder#inflateAndBind(int, Object)
	 */
	public static View inflateAndBindWithoutPreInitializingViews(Context context, int layoutId, Object presentationModel) {
		return inflateAndBind(context, layoutId, presentationModel, false);
	}

	public static View inflateAndBindWithoutPreInitializingViews(Context context, int layoutId, Object presentationModel, 
			ViewGroup root, boolean attachToRoot) {
		return inflateAndBind(context, layoutId, presentationModel, false, root, attachToRoot);
	}

	public static void inflateAndBindMenu(Menu menu, MenuInflater menuInflater, int menuRes, Object presentationModel, Context context) {
		MenuBinder menuBinder = newBinderFactory().createMenuBinder(menu, menuInflater, context);
		menuBinder.inflateAndBind(menuRes, presentationModel);
	}

	public static void inflateAndBindMenuWithoutPreInitializingViews(Menu menu, MenuInflater menuInflater, int menuRes, Object presentationModel,
			Context context) {
		MenuBinder menuBinder = newBinderFactory().createMenuBinder(menu, menuInflater, context, false);
		menuBinder.inflateAndBind(menuRes, presentationModel);
	}

	private Binders() {
	}
}
