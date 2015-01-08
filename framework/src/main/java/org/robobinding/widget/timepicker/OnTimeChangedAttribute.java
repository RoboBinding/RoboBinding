package org.robobinding.widget.timepicker;

import android.widget.TimePicker;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.event.EventViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class OnTimeChangedAttribute implements EventViewAttribute<TimePicker>, ViewListenersAware<TimePickerListeners> {
    private TimePickerListeners viewListeners;

    @Override
    public void setViewListeners(TimePickerListeners viewListeners) {
        this.viewListeners = viewListeners;
    }

    @Override
    public void bind(final TimePicker view, final Command command) {
        viewListeners.addOnTimeChangedListener( new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                TimeChangedEvent event = new TimeChangedEvent(timePicker, hourOfDay, minute);
                command.invoke(event);
            }
        });
    }

    @Override
    public Class<TimeChangedEvent> getEventType() {
        return TimeChangedEvent.class;
    }



}