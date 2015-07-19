package org.robobinding.widget.textview;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttribute implements OneWayPropertyViewAttribute<TextView, CharSequence> {
	@Override
	public void updateView(TextView view, CharSequence newValue) {
		view.setText(newValue);
	}
}