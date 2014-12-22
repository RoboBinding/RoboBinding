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
public class OnTimeChangedAttributeTest extends AbstractTimePickerAttributeTest {
	private OnTimeChangedAttribute attribute;
    private EventCommand eventCommand;

    @Before
    public void setUp() {
        eventCommand = new EventCommand();
        attribute = withListenersSet(new OnTimeChangedAttribute());
    }

    @Test
    public void givenBoundAttribute_whenChangeHourTime_thenEventReceived() {
        attribute.bind(view, eventCommand);
        view.setCurrentHour(RandomValues.anyInteger());
        assertEventReceived();
    }

    @Test
    public void givenBoundAttribute_whenChangeMinuteTime_thenEventReceived() {
        attribute.bind(view, eventCommand);
        view.setCurrentMinute(RandomValues.anyInteger());
        assertEventReceived();
    }

    private void assertEventReceived() {
        eventCommand.assertEventReceived(TimeChangedEvent.class);
    }
}