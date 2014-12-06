package org.robobinding.widget.timepicker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.property.ValueModelUtils;
import org.robolectric.annotation.Config;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
@Config(manifest = Config.NONE)
public class TwoWayCurrentMinuteAttributeTest extends AbstractTimePickerAttributeTest {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
        TwoWayCurrentMinuteAttribute attribute = new TwoWayCurrentMinuteAttribute();
        int newProgressValue = RandomValues.anyInteger();

        attribute.updateView(view, newProgressValue);

        assertThat(view.getProgress(), is(newProgressValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
        TwoWayCurrentMinuteAttribute attribute = withListenersSet(new TwoWayCurrentMinuteAttribute());
        ValueModel<Integer> valueModel = ValueModelUtils.create();
        attribute.observeChangesOnTheView(view, valueModel);

        int newProgressValue = RandomValues.anyInteger();
        view.setProgress(newProgressValue);

        assertThat(valueModel.getValue(), equalTo(newProgressValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
        TwoWayCurrentMinuteAttribute attribute = withListenersSet(new TwoWayCurrentMinuteAttribute());
        attribute.observeChangesOnTheView(null, null);

        assertTrue(viewListeners.addOnTimeChangedListenerInvoked);
    }
}