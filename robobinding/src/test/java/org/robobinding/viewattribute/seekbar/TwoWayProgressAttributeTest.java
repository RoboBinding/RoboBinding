package org.robobinding.viewattribute.seekbar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractPropertyViewAttributeWithViewListenersAwareTest;

import android.widget.SeekBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayProgressAttributeTest extends
	AbstractPropertyViewAttributeWithViewListenersAwareTest<SeekBar, TwoWayProgressAttribute, MockSeekBarListeners> {
    @Test
    public void whenUpdatingValueModel_thenSetProgressOnSeekBar() {
	int newProgressValue = RandomValues.anyInteger();

	attribute.valueModelUpdated(newProgressValue);

	assertThat(view.getProgress(), is(newProgressValue));
    }

    @Test
    public void whenUpdatingTheSeekBar_thenUpdateValueModel() {
	ValueModel<Integer> valueModel = twoWayBindToProperty(Integer.class, RandomValues.anyInteger());

	int newProgressValue = RandomValues.anyInteger();
	view.setProgress(newProgressValue);

	assertThat(valueModel.getValue(), equalTo(newProgressValue));
    }

    @Test
    public void whenTwoWayBinding_thenRegisterWithViewListeners() {
	twoWayBindToProperty(Integer.class);

	assertTrue(viewListeners.addOnSeekBarChangeListenerInvoked);
    }
}
