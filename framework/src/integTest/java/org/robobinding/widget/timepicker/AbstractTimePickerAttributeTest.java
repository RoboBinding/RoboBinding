package org.robobinding.widget.timepicker;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.widget.TimePicker;

/**
 * @since 1.0
 * @version
 * @author Joachim Hill-Grannec
 *
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractTimePickerAttributeTest {
    protected TimePicker view;
    protected MockTimePickerAddOn viewAddOn;

    @Before
    public void initializeViewAndListeners() {
        view = new TimePicker(RuntimeEnvironment.application);
        viewAddOn = new MockTimePickerAddOn(view);
    }
}
