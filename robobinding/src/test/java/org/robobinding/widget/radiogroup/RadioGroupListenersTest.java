package org.robobinding.widget.radiogroup;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class RadioGroupListenersTest {
    @Test
    public void shouldSupportMultipleOnCheckedChangeListeners() {
	RadioGroup view = new RadioGroup(null);
	RadioGroupListeners viewListeners = new RadioGroupListeners(view);

	MockOnCheckedChangeListener listener1 = new MockOnCheckedChangeListener();
	MockOnCheckedChangeListener listener2 = new MockOnCheckedChangeListener();

	viewListeners.addOnCheckedChangeListener(listener1);
	viewListeners.addOnCheckedChangeListener(listener2);

	view.check(RandomValues.anyIntegerGreaterThanZero());

	assertTrue(listener1.checkedChangeEventFired);
	assertTrue(listener2.checkedChangeEventFired);
    }

    private static class MockOnCheckedChangeListener implements OnCheckedChangeListener {
	private boolean checkedChangeEventFired;
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	    checkedChangeEventFired = true;
	}
    }
}