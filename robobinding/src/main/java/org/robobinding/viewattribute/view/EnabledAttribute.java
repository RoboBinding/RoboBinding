package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EnabledAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Boolean> {
    @Override
    protected void valueModelUpdated(Boolean newValue) {
	view.setEnabled(newValue);
    }

}
