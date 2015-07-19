package org.robobinding.widget.menuitem;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.view.MenuItem;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuItemBinding implements ViewBinding<MenuItem> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<MenuItem> mappings) {
		mappings.mapOneWayMultiTypeProperty(TitleAttribute.class, "title");

		mappings.mapEvent(OnMenuItemClickAttribute.class, "onMenuItemClick");

	}
}
