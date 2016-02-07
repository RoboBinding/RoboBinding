package org.robobinding.binder;

import org.robobinding.MenuBinder;
import org.robobinding.ViewBinder;
import org.robobinding.viewbinding.ViewBindingMap;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.ViewAddOns;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.common.base.Preconditions;

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
		checkContext(context);

		SingletonAssembler assembler = new SingletonAssembler(viewBindingMap, viewAddOns, context);
		return assembler.createViewBinder();
	}

	private void checkContext(Context context) {
		Preconditions.checkNotNull(context, "context must not be null");
	}

	public MenuBinder createMenuBinder(Menu menu, MenuInflater menuInflater, Context context) {
		Preconditions.checkNotNull(menuInflater, "menuInflater must not be null");
		Preconditions.checkNotNull(menu, "menu must not be null");
		checkContext(context);

		SingletonAssembler assembler = new SingletonAssembler(viewBindingMap, viewAddOns, context);
		return assembler.createMenuBinder(menuInflater, menu);
	}
	
	public ViewAddOn viewAddOnFor(Object view) {
		return viewAddOns.getMostSuitable(view);
	}
}
