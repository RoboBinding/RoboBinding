package org.robobinding.viewattribute;

import org.robobinding.property.ValueModel;

import android.view.View;

public class PropertyViewAttributeSpy extends AbstractPropertyViewAttribute<View, Integer>
{
	int viewUpdateNotificationCount;
	int updatedValue;
	boolean viewInitialized;
	private ValueModel<Integer> valueModelUpdatedByView;
	
	public void simulateViewUpdate(int newValue)
	{
		valueModelUpdatedByView.setValue(newValue);
	}

	@Override
	protected void observeChangesOnTheView(ValueModel<Integer> valueModel)
	{
		valueModelUpdatedByView = valueModel;
	}

	@Override
	protected void valueModelUpdated(Integer newValue)
	{
		this.updatedValue = newValue;
		viewUpdateNotificationCount++;
		viewInitialized = true;
	}
}