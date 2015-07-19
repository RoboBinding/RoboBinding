package org.robobinding.widget.timepicker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.EventCommand;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
        attribute = new OnTimeChangedAttribute();
        eventCommand = new EventCommand();
    }

    @Test
    public void givenBoundAttribute_whenChangeHourTime_thenEventReceived() {
    	bindAttribute();
        view.setCurrentHour(RandomValues.anyInteger());
        assertEventReceived();
    }

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

    @Test
    public void givenBoundAttribute_whenChangeMinuteTime_thenEventReceived() {
    	bindAttribute();
        view.setCurrentMinute(RandomValues.anyInteger());
        assertEventReceived();
    }

    private void assertEventReceived() {
        eventCommand.assertEventReceived(TimeChangedEvent.class);
    }
}