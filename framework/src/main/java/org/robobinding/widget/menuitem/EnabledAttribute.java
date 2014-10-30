package org.robobinding.widget.menuitem;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.view.MenuItem;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EnabledAttribute implements OneWayPropertyViewAttribute<MenuItem, Boolean> {
	@Override
	public void updateView(MenuItem view, Boolean newValue) {
		view.setEnabled(newValue);
	}

}
