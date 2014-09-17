package org.robobinding.widget.textview;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TextColorAttribute implements PropertyViewAttribute<TextView, Integer> {
	@Override
	public void updateView(TextView view, Integer newColor) {
		view.setTextColor(newColor);
	}
}
