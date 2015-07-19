package org.robobinding.widgetaddon.timepicker;

import org.robobinding.widgetaddon.view.ViewAddOnForView;

import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class TimePickerAddOn extends ViewAddOnForView {
    protected final TimePicker timePicker;
    private OnTimeChangedListeners onTimeChangedListeners;

    public TimePickerAddOn(TimePicker timePicker) {
        super(timePicker);
        this.timePicker = timePicker;
    }

    public void addOnTimeChangedListener(OnTimeChangedListener listener) {
        ensureOnTimeChangedListenersInitialized();
        onTimeChangedListeners.addListener(listener);
    }

    private void ensureOnTimeChangedListenersInitialized() {
        if (onTimeChangedListeners == null) {
            onTimeChangedListeners = new OnTimeChangedListeners();
            timePicker.setOnTimeChangedListener(onTimeChangedListeners);
        }
    }
}
