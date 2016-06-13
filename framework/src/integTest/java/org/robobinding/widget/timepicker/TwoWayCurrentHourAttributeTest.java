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
public class TwoWayCurrentHourAttributeTest extends AbstractTimePickerAttributeTest {
	private TwoWayCurrentHourAttribute attribute;
	
	@Before
	public void setUp() {
		attribute = new TwoWayCurrentHourAttribute();
	}
	
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
        int newCurrentHourValue = RandomValues.integerBetween(1,23);
        
        attribute.updateView(view, newCurrentHourValue, null);
        
        assertThat(view.getCurrentHour(), is(newCurrentHourValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
        ValueModel<Integer> valueModel = ValueModelUtils.create();
        attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

        view.setIs24HourView(true);
        int newCurrentHourValue = RandomValues.integerBetween(0,23);
        view.setCurrentHour(newCurrentHourValue);

        assertThat(valueModel.getValue(), equalTo(newCurrentHourValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
        attribute.observeChangesOnTheView(viewAddOn, null, null);

        assertTrue(viewAddOn.addOnTimeChangedListenerInvoked);
    }
}
