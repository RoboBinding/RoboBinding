package org.robobinding.viewattribute.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

import android.widget.CheckBox;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributeTest extends
	AbstractCommandViewAttributeWithViewListenersAwareTest<CheckBox, OnCheckedChangeAttribute, MockCompoundButtonListeners> {
    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
	bindAttribute();

	changeCheckedState();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
    }

    private void changeCheckedState() {
	view.setChecked(!view.isChecked());
    }

    private void assertEventReceived() {
	assertEventReceived(CheckedChangeEvent.class);
    }
}
