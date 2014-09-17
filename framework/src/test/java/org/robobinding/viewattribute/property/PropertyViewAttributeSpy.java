package org.robobinding.viewattribute.property;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class PropertyViewAttributeSpy implements TwoWayPropertyViewAttribute<View, Integer> {
	int viewUpdateNotificationCount;
	int viewValue;
	private ValueModel<Integer> valueModelUpdatedByView;

	@Override
	public void observeChangesOnTheView(View view, ValueModel<Integer> valueModel) {
		valueModelUpdatedByView = valueModel;
	}

	@Override
	public void updateView(View view, Integer newValue) {
		this.viewValue = newValue;
		viewUpdateNotificationCount++;
	}

	public void simulateViewUpdate(int newValue) {
		if (isObservingChangesOnTheView()) {
			valueModelUpdatedByView.setValue(newValue);
		}
	}

	private boolean isObservingChangesOnTheView() {
		return valueModelUpdatedByView != null;
	}

}