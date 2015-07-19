package org.robobinding.widget.viewanimator;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.ViewAnimator;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class DisplayedChildAttribute implements OneWayPropertyViewAttribute<ViewAnimator, Integer> {

	@Override
	public void updateView(ViewAnimator view, Integer newValue) {
		if (view.getDisplayedChild() != newValue) {
			view.setDisplayedChild(newValue);
		}
	}
}
