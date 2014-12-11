package org.robobinding.widget.timepicker;

import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class MockTimePickerListeners extends TimePickerListeners {
    public boolean addOnTimeChangedListenerInvoked;

    public MockTimePickerListeners(TimePicker timePicker) {
        super(timePicker);
    }

    @Override
    public void addOnTimeChangedListener(OnTimeChangedListener listener) {
        addOnTimeChangedListenerInvoked = true;
        timePicker.setOnTimeChangedListener(listener);
    }
}
