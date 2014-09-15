package org.robobinding.widget.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class CompoundButtonListenersTest {
	@Test
	public void shouldSupportMultipleOnCheckedChangeListeners() {
		CheckBox view = new CheckBox(Robolectric.application);
		CompoundButtonListeners viewListeners = new CompoundButtonListeners(view);

		MockOnCheckedChangeListener listener1 = new MockOnCheckedChangeListener();
		MockOnCheckedChangeListener listener2 = new MockOnCheckedChangeListener();

		viewListeners.addOnCheckedChangeListener(listener1);
		viewListeners.addOnCheckedChangeListener(listener2);

		view.setChecked(!view.isChecked());

		assertTrue(listener1.checkedChangeEventFired);
		assertTrue(listener2.checkedChangeEventFired);
	}

	private static class MockOnCheckedChangeListener implements
			OnCheckedChangeListener {
		private boolean checkedChangeEventFired;

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			checkedChangeEventFired = true;
		}
	}
}
