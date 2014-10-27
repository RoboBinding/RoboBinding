package org.robobinding.widget.menuitem;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.view.MenuItem;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class VisibleAttribute implements PropertyViewAttribute<MenuItem, Boolean> {
	@Override
	public void updateView(MenuItem view, Boolean newValue) {
		view.setVisible(newValue);
	}

}
