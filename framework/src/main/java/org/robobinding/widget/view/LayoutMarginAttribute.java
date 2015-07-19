package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class LayoutMarginAttribute implements OneWayPropertyViewAttribute<View, Integer> {
	@Override
	public void updateView(View view, Integer newValue) {
		if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

			params.setMargins(newValue, newValue, newValue, newValue);
		}
	}
}
