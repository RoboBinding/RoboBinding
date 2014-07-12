package org.robobinding.widget.textview;

import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttribute implements PropertyViewAttribute<TextView, CharSequence> {
    @Override
    public void updateView(TextView view, CharSequence newValue) {
	view.setText(newValue);
    }
}
