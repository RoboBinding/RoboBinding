package org.robobinding.widget.timepicker;
import android.widget.TimePicker;

import org.robobinding.widget.view.AbstractViewEvent;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class TimeChangedEvent extends AbstractViewEvent {
    private int currentHour;
    private int currentMinute;

    public TimeChangedEvent(TimePicker view, int currentHour, int currentMinute) {
        super(view);
        this.currentHour = currentHour;
        this.currentMinute = currentMinute;
    }

    @Override
    public TimePicker getView() {
        return (TimePicker) super.getView();
    }

    public int getCurrentHour() {
        return currentHour;
    }
    public int getCurrentMinute() {
        return currentMinute;
    }

}
