package org.robobinding.viewattribute.progressbar;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MaxAttribute extends AbstractReadOnlyPropertyViewAttribute<ProgressBar, Integer> {
    @Override
    protected void valueModelUpdated(Integer maxValue) {
	view.setMax(maxValue);
    }

}
