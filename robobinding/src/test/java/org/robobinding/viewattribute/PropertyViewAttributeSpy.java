package org.robobinding.viewattribute;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class PropertyViewAttributeSpy extends AbstractPropertyViewAttribute<View, Integer> {
    int viewUpdateNotificationCount;
    int updatedValue;
    boolean viewInitialized;
    private ValueModel<Integer> valueModelUpdatedByView;

    public PropertyViewAttributeSpy() {
    }

    public PropertyViewAttributeSpy(boolean withAlwaysPreInitializingView) {
	super(withAlwaysPreInitializingView);
    }

    public void simulateViewUpdate(int newValue) {
	valueModelUpdatedByView.setValue(newValue);
    }

    @Override
    protected void observeChangesOnTheView(ValueModel<Integer> valueModel) {
	valueModelUpdatedByView = valueModel;
    }

    @Override
    protected void valueModelUpdated(Integer newValue) {
	this.updatedValue = newValue;
	viewUpdateNotificationCount++;
	viewInitialized = true;
    }
}