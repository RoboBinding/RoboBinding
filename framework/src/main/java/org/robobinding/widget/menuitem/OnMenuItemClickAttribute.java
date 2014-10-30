package org.robobinding.widget.menuitem;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widgetaddon.menuitem.MenuItemAddOn;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnMenuItemClickAttribute implements EventViewAttribute<MenuItem, MenuItemAddOn> {
	@Override
	public void bind(MenuItemAddOn viewAddOn, final Command command, MenuItem view) {
		viewAddOn.addOnMenuItemClickListener(new OnMenuItemClickListener() {

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
