package org.robobinding.widget.timepicker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robolectric.annotation.Config;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Joachim Hill-Grannec
 */
@Config(manifest = Config.NONE)
public class TwoWayCurrentMinuteAttributeTest extends AbstractTimePickerAttributeTest {
	private TwoWayCurrentMinuteAttribute attribute;
	
	@Before
	public void setUp() {
		attribute = new TwoWayCurrentMinuteAttribute();
	}
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
        int newCurrentMinuteValue = RandomValues.integerBetween(0,59);

        attribute.updateView(view, newCurrentMinuteValue, null);

        assertThat(view.getCurrentMinute(), is(newCurrentMinuteValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
        ValueModel<Integer> valueModel = ValueModelUtils.create();
        attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

        int newCurrentMinuteValue = RandomValues.integerBetween(0,59);
        view.setCurrentMinute(newCurrentMinuteValue);

        assertThat(valueModel.getValue(), equalTo(newCurrentMinuteValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
        attribute.observeChangesOnTheView(viewAddOn, null, null);

        assertTrue(viewAddOn.addOnTimeChangedListenerInvoked);
    }
}