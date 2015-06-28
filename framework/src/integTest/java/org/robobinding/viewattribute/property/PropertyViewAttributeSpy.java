package org.robobinding.viewattribute.property;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PropertyViewAttributeSpy implements OneWayPropertyViewAttribute<View, Integer> {
	int viewUpdateNotificationCount;
	int viewValue;

	@Override
	public void updateView(View view, Integer newValue) {
		viewValue = newValue;
		viewUpdateNotificationCount++;
	}
}