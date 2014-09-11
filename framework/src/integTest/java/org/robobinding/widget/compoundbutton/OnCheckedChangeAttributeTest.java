package org.robobinding.widget.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.CheckBox;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributeTest extends
	AbstractEventViewAttributeWithViewListenersAwareTest<CheckBox, OnCheckedChangeAttribute, MockCompoundButtonListeners> {
    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
	bindAttribute();

	changeCheckedState();

	assertEventReceived();
    }

    private void changeCheckedState() {
        view.setChecked(!view.isChecked());
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
