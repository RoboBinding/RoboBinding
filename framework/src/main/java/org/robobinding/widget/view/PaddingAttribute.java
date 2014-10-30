package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class PaddingAttribute implements OneWayPropertyViewAttribute<View, Integer> {
	@Override
	public void updateView(View view, Integer newValue) {
		view.setPadding(newValue, newValue, newValue, newValue);
	}
}
