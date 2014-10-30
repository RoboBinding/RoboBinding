package org.robobinding.widgetaddon.menuitem;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class MenuItemListeners {
	private MenuItem menuItem;
	private OnMenuItemClickListeners onMenuItemClickListeners;

	public MenuItemListeners(MenuItem menuItem) {
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
