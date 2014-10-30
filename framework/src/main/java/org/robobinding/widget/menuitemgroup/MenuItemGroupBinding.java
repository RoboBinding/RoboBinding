package org.robobinding.widget.menuitemgroup;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuItemGroupBinding implements ViewBinding<MenuItemGroup> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<MenuItemGroup> mappings) {
		mappings.mapOneWayProperty(EnabledAttribute.class, "enabled");
		mappings.mapOneWayProperty(VisibleAttribute.class, "visible");
	}
}
