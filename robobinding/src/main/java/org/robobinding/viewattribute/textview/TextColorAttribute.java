package org.robobinding.viewattribute.textview;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TextColorAttribute extends AbstractReadOnlyPropertyViewAttribute<TextView, Integer> {
    @Override
    protected void valueModelUpdated(Integer newColor) {
	view.setTextColor(newColor);
    }
}
