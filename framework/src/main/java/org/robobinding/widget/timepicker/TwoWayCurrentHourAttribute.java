package org.robobinding.widget.timepicker;

import android.widget.TimePicker;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class TwoWayCurrentHourAttribute implements TwoWayPropertyViewAttribute<TimePicker, Integer>, ViewListenersAware<TimePickerListeners> {
    private TimePickerListeners viewListeners;
    @Override
    public void setViewListeners(TimePickerListeners viewListeners) {
        this.viewListeners = viewListeners;
    }
    @Override
    public void updateView(TimePicker view, Integer newValue) {
        view.setCurrentHour(newValue);
    }

    @Override
    public void observeChangesOnTheView(TimePicker timePicker, final ValueModel<Integer> integerValueModel) {
        viewListeners.addOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                integerValueModel.setValue(hourOfDay);
            }
        });
    }
}
