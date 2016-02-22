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
		return inflateAndBind(context, layoutId, presentationModel, null, true);
	}

	private static View inflateAndBind(Context context, int layoutId, Object presentationModel, 
			ViewGroup root, boolean attachToRoot) {
		ViewBinder viewBinder = newBinderFactory().createViewBinder(context);
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

	public static void inflateAndBindMenu(Menu menu, MenuInflater menuInflater, int menuRes, Object presentationModel, Context context) {
		MenuBinder menuBinder = newBinderFactory().createMenuBinder(menu, menuInflater, context);
		menuBinder.inflateAndBind(menuRes, presentationModel);
	}

	private Binders() {
	}
}
