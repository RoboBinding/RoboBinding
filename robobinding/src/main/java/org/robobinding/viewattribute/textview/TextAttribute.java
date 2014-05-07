package org.robobinding.viewattribute.textview;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttribute extends AbstractReadOnlyPropertyViewAttribute<TextView, CharSequence> {
    @Override
    protected void valueModelUpdated(CharSequence newValue) {
	view.setText(newValue);
    }
}
