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
public class TwoWayCurrentHourAttributeTest extends AbstractTimePickerAttributeTest {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
        TwoWayCurrentHourAttribute attribute = new TwoWayCurrentHourAttribute();
        int newCurrentHourValue = RandomValues.integerBetween(1,24);
        
        attribute.updateView(view, newCurrentHourValue);
        
        assertThat(view.getCurrentHour(), is(newCurrentHourValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
        TwoWayCurrentHourAttribute attribute = withListenersSet(new TwoWayCurrentHourAttribute());
        ValueModel<Integer> valueModel = ValueModelUtils.create();
        
        attribute.observeChangesOnTheView(view, valueModel);

        int newCurrentHourValue = RandomValues.integerBetween(1,24);
        view.setCurrentHour(newCurrentHourValue);

        assertThat(valueModel.getValue(), equalTo(newCurrentHourValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
        TwoWayCurrentHourAttribute attribute = withListenersSet(new TwoWayCurrentHourAttribute());
        attribute.observeChangesOnTheView(null, null);

        assertTrue(viewListeners.addOnTimeChangedListenerInvoked);
    }
}
