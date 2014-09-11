package org.robobinding.widget.seekbar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeWithViewListenersAwareTest;

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
    public void whenUpdateView_thenViewShouldReflectChanges() {
	int newProgressValue = RandomValues.anyInteger();

	attribute.updateView(view, newProgressValue);

	assertThat(view.getProgress(), is(newProgressValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {

	ValueModel<Integer> valueModel = ValueModelUtils.create();
	attribute.observeChangesOnTheView(view, valueModel);

	int newProgressValue = RandomValues.anyInteger();
	view.setProgress(newProgressValue);

	assertThat(valueModel.getValue(), equalTo(newProgressValue));
    }

    @Test
    public void whenObserveChangesOnTheView_thenRegisterWithViewListeners() {
	attribute.observeChangesOnTheView(null, null);

	assertTrue(viewListeners.addOnSeekBarChangeListenerInvoked);
    }
}
