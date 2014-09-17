package org.robobinding.widget.menuitem;

import org.robobinding.viewattribute.ViewListeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuItemListeners implements ViewListeners {
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
