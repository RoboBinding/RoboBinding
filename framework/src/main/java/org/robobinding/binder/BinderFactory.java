package org.robobinding.binder;

import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;
import org.robobinding.util.Preconditions;
import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOns;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderFactory {
	private final ViewBindingMap viewBindingMap;
	private final ViewAddOns viewAddOns;

	BinderFactory(ViewBindingMap viewBindingMap, ViewAddOns viewAddOns) {
		this.viewBindingMap = viewBindingMap;
		this.viewAddOns = viewAddOns;
	}

	public ViewBinder createViewBinder(Context context) {
		return createViewBinder(context, true);
	}

	public ViewBinder createViewBinder(Context context, boolean withPreInitializingViews) {
		checkContext(context);

		SingletonAssembler assembler = new SingletonAssembler(viewBindingMap, viewAddOns, context, withPreInitializingViews);
		return assembler.createViewBinder();
	}

	private void checkContext(Context context) {
		Preconditions.checkNotNull(context, "context must not be null");
	}

	public MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater, Context context) {
		return createMenuBinder(menu, menuInflater, context, true);
	}

	public MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater, Context context, boolean withPreInitializingViews) {
		Preconditions.checkNotNull(menuInflater, "menuInflater must not be null");
		Preconditions.checkNotNull(menu, "menu must not be null");
		checkContext(context);

		SingletonAssembler assembler = new SingletonAssembler(viewBindingMap, viewAddOns, context, withPreInitializingViews);
		return assembler.createMenuBinder(menuInflater, menu);
	}
	
	public ViewAddOn viewAddOnFor(Object view) {
		return viewAddOns.getMostSuitable(view);
	}
}
