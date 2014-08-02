package org.robobinding.widget.viewAnimator;

import android.widget.ViewAnimator;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class DisplayedChildAttribute implements PropertyViewAttribute<ViewAnimator,Integer> {

	@Override
	public void updateView(ViewAnimator view, Integer newValue) {
		view.setDisplayedChild(newValue);
	}
}
