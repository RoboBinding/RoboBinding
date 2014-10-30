package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SelectedAttribute implements OneWayPropertyViewAttribute<View, Boolean> {
	@Override
	public void updateView(View view, Boolean newValue) {
		view.setSelected(newValue);
	}
}
