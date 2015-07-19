package org.robobinding.widget.timepicker;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.TimePicker;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
public class TimePickerBinding implements ViewBinding<TimePicker> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<TimePicker> mappings) {
        mappings.mapTwoWayProperty(TwoWayCurrentMinuteAttribute.class, "currentMinute");
        mappings.mapTwoWayProperty(TwoWayCurrentHourAttribute.class, "currentHour");
        
        mappings.mapEvent(OnTimeChangedAttribute.class, "onTimeChanged");
    }

}
