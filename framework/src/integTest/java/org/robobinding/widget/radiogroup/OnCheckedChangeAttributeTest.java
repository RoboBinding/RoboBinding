package org.robobinding.widget.radiogroup;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.RadioGroup;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributeTest  extends
	AbstractEventViewAttributeWithViewListenersAwareTest<RadioGroup, OnCheckedChangeAttribute, MockRadioGroupListeners> {
    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
	bindAttribute();

	changeCheckedId();

	assertEventReceived();
    }

    private void changeCheckedId() {
        view.check(RandomValues.anyIntegerGreaterThanZero());
    }

    private void assertEventReceived() {
        assertEventReceived(CheckedChangeEvent.class);
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
    }
}