package org.robobinding.widget.timepicker;

import org.robobinding.widgetaddon.timepicker.TimePickerAddOn;

import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class MockTimePickerAddOn extends TimePickerAddOn {
    public boolean addOnTimeChangedListenerInvoked;

    public MockTimePickerAddOn(TimePicker timePicker) {
        super(timePicker);
    }

    @Override
    public void addOnTimeChangedListener(OnTimeChangedListener listener) {
        addOnTimeChangedListenerInvoked = true;
        timePicker.setOnTimeChangedListener(listener);
    }
}
