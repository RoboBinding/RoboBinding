package org.robobinding.widget.timepicker;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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
    protected MockTimePickerListeners viewListeners;

    @Before
    public void initializeViewAndListeners() {
        view = new TimePicker(Robolectric.application);
        viewListeners = new MockTimeBarListeners(view);
    }

    public <T extends ViewListenersAware<SeekBarListeners>> T withListenersSet(T attribute) {
        attribute.setViewListeners(viewListeners);
        return attribute;
    }
}
