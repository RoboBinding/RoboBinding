package org.robobinding.viewattribute.property;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
public class TwoWayPropertyViewAttributeSpy implements TwoWayPropertyViewAttribute<View, Integer> {
	int viewUpdateNotificationCount;
	int viewValue;
	private ValueModel<Integer> valueModel;

	@Override
	public void observeChangesOnTheView(View view, ValueModel<Integer> valueModel) {
		this.valueModel = valueModel;
	}

	@Override
	public void updateView(View view, Integer newValue) {
		viewUpdateNotificationCount++;
		this.viewValue = newValue;
		notifyViewChange(newValue);
	}

	private void notifyViewChange(int newValue) {
		valueModel.setValue(newValue);
	}

	public void simulateViewUpdate(int newValue) {
		this.viewValue = newValue;
		notifyViewChange(newValue);
	}

}
