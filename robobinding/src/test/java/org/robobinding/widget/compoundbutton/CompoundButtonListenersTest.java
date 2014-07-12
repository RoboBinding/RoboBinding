package org.robobinding.widget.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class CompoundButtonListenersTest {
    @Test
    public void shouldSupportMultipleOnCheckedChangeListeners() {
	CheckBox view = new CheckBox(null);
	CompoundButtonListeners viewListeners = new CompoundButtonListeners(view);

	MockOnCheckedChangeListener listener1 = new MockOnCheckedChangeListener();
	MockOnCheckedChangeListener listener2 = new MockOnCheckedChangeListener();

	viewListeners.addOnCheckedChangeListener(listener1);
	viewListeners.addOnCheckedChangeListener(listener2);

	view.setChecked(!view.isChecked());

	assertTrue(listener1.checkedChangeEventFired);
	assertTrue(listener2.checkedChangeEventFired);
    }

    private static class MockOnCheckedChangeListener implements OnCheckedChangeListener {
	private boolean checkedChangeEventFired;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    checkedChangeEventFired = true;
	}
    }
}
