package org.robobinding.widget.progressbar;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SecondaryProgressAttribute implements OneWayPropertyViewAttribute<ProgressBar, Integer> {
	@Override
	public void updateView(ProgressBar view, Integer newSecondaryProgress) {
		view.setSecondaryProgress(newSecondaryProgress);
	}

}
