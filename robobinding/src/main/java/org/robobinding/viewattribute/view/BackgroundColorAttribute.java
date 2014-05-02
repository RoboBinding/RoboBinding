package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BackgroundColorAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Integer> {
    @Override
    protected void valueModelUpdated(Integer newColor) {
	view.setBackgroundColor(newColor);
    }
}
