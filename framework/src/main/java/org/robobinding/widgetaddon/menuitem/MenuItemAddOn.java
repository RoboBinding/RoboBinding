package org.robobinding.widgetaddon.menuitem;

import org.robobinding.widgetaddon.ViewAddOn;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MenuItemAddOn implements ViewAddOn {
	private MenuItem menuItem;
	private OnMenuItemClickListeners onMenuItemClickListeners;

	public MenuItemAddOn(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public void addOnMenuItemClickListener(OnMenuItemClickListener listener) {
		ensureOnMenuItemClickListenersInitialized();
		onMenuItemClickListeners.addListener(listener);
	}

	private void ensureOnMenuItemClickListenersInitialized() {
		if (onMenuItemClickListeners == null) {
			onMenuItemClickListeners = new OnMenuItemClickListeners();
			menuItem.setOnMenuItemClickListener(onMenuItemClickListeners);
		}
	}
}
