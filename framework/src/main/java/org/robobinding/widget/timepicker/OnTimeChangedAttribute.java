package org.robobinding.widget.timepicker;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.widgetaddon.timepicker.TimePickerAddOn;

import android.widget.TimePicker;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class OnTimeChangedAttribute implements EventViewAttribute<TimePicker, TimePickerAddOn> {
    @Override
    public void bind(TimePickerAddOn viewAddOn, final Command command, TimePicker view) {
    	viewAddOn.addOnTimeChangedListener( new TimePicker.OnTimeChangedListener() {
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