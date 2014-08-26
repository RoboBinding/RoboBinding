package org.robobinding.widget.menuitemgroup;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class VisibleAttribute implements PropertyViewAttribute<MenuItemGroup, Boolean> {
    @Override
    public void updateView(MenuItemGroup view, Boolean newValue) {
	view.setVisible(newValue);
    }

}
