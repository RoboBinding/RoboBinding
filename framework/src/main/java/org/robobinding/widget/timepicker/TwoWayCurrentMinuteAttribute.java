package org.robobinding.widget.timepicker;

import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class TwoWayCurrentMinuteAttribute implements TwoWayPropertyViewAttribute<TimePicker, Integer>, ViewListenersAware<TimePickerListeners> {
    private TimePickerListeners viewListeners;
    @Override
    public void setViewListeners(TimePickerListeners viewListeners) {
        this.viewListeners = viewListeners;
    }
    @Override
    public void updateView(TimePicker view, Integer newValue) {
        view.setCurrentMinute(newValue);
    }

    @Override
    public void observeChangesOnTheView(TimePicker timePicker, final ValueModel<Integer> integerValueModel) {
        viewListeners.addOnTimeChangedListener(new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                integerValueModel.setValue(minute);
            }
        });
    }
}