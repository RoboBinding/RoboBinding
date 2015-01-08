package org.robobinding.widget.timepicker;

import android.widget.TimePicker;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class TimePickerBinding implements ViewBinding<TimePicker> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<TimePicker> mappings) {
        mappings.mapProperty(TwoWayCurrentMinuteAttribute.class, "currentMinute");
        mappings.mapProperty(TwoWayCurrentHourAttribute.class, "currentHour");
        mappings.mapEvent(OnTimeChangedAttribute.class, "onTimeChanged");
    }

}
