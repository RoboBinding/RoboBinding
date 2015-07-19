package org.robobinding.viewattribute.property;

import org.robobinding.property.ValueModel;
import org.robobinding.widgetaddon.ViewAddOn;

import android.view.View;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
public class TwoWayPropertyViewAttributeSpy implements TwoWayPropertyViewAttribute<View, ViewAddOn, Integer> {
	int viewUpdateNotificationCount;
	int viewValue;
	private ValueModel<Integer> valueModel;

	@Override
	public void observeChangesOnTheView(ViewAddOn viewAddOn, ValueModel<Integer> valueModel, View view) {
		this.valueModel = valueModel;
	}

	@Override
	public void updateView(View view, Integer newValue, ViewAddOn viewAddOn) {
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
