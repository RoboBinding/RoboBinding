package org.robobinding.widget.timepicker;

import org.robobinding.util.RandomValues;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.EventCommand;
import org.robobinding.widget.timepicker.OnTimeChangedAttribute;
import org.robobinding.widget.timepicker.TimeChangedEvent;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.TimePicker;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class OnTimeChangedAttributeTest {
    private TimePicker view;
    private EventCommand eventCommand;

    @Before
    public void setUp() {
        view = new TimePicker(Robolectric.application);
        eventCommand = new EventCommand();
    }

    @Test
    public void givenBoundAttribute_whenChangeHourTime_thenEventReceived() {
        OnTimeChangedAttribute attribute = new OnTimeChangedAttribute();
        attribute.bind(view, eventCommand);
        changeHourText();
        assertEventReceived();
    }

    @Test
    public void givenBoundAttribute_whenChangeMinuteTime_thenEventReceived() {
        OnTimeChangedAttribute attribute = new OnTimeChangedAttribute();
        attribute.bind(view, eventCommand);
        changeMinuteText();
        assertEventReceived();
    }

    private void changeHourText() {
       view.setCurrentHour(RandomValues.anyInteger());
    }

    private void changeMinuteText() {
        view.setCurrentMinute(RandomValues.anyInteger());
    }

    private void assertEventReceived() {
        eventCommand.assertEventReceived(TimeChangedEvent.class);
    }
}