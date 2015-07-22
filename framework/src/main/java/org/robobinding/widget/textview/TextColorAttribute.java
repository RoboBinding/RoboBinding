package org.robobinding.widget.textview;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TextColorAttribute implements OneWayPropertyViewAttribute<TextView, Integer> {
	@Override
	public void updateView(TextView view, Integer newColor) {
		view.setTextColor(newColor);
	}
}