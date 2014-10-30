package org.robobinding.widget.progressbar;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MaxAttribute implements OneWayPropertyViewAttribute<ProgressBar, Integer> {
	@Override
	public void updateView(ProgressBar view, Integer newMaxValue) {
		view.setMax(newMaxValue);
	}
}
