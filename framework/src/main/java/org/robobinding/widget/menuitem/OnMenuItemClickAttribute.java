package org.robobinding.widget.menuitem;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class OnMenuItemClickAttribute implements EventViewAttribute<MenuItem>, ViewListenersAware<MenuItemListeners> {
	private MenuItemListeners viewListeners;

	@Override
	public void setViewListeners(MenuItemListeners viewListeners) {
		this.viewListeners = viewListeners;
	}

	@Override
	public void bind(MenuItem view, final Command command) {
		viewListeners.addOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Object result = command.invoke(item);
				return Boolean.TRUE.equals(result);
			}
		});
	}

	@Override
	public Class<MenuItem> getEventType() {
		return MenuItem.class;
	}
}
