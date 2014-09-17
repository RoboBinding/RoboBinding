package org.robobinding.widget.menuitemgroup;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuItemGroupBinding implements ViewBinding<MenuItemGroup> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<MenuItemGroup> mappings) {
		mappings.mapProperty(EnabledAttribute.class, "enabled");
		mappings.mapProperty(VisibleAttribute.class, "visible");
	}
}
