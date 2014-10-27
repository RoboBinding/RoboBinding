package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FocusableAttribute implements PropertyViewAttribute<View, Boolean> {
	@Override
	public void updateView(View view, Boolean newValue) {
		view.setFocusable(newValue);
	}
}
