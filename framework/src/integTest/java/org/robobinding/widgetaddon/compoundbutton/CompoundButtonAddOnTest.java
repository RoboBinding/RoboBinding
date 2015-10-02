package org.robobinding.widgetaddon.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class CompoundButtonAddOnTest {
	@Test
	public void shouldSupportMultipleOnCheckedChangeListeners() {
		CheckBox view = new CheckBox(RuntimeEnvironment.application);
		CompoundButtonAddOn viewAddOn = new CompoundButtonAddOn(view);

		MockOnCheckedChangeListener listener1 = new MockOnCheckedChangeListener();
		MockOnCheckedChangeListener listener2 = new MockOnCheckedChangeListener();

		viewAddOn.addOnCheckedChangeListener(listener1);
		viewAddOn.addOnCheckedChangeListener(listener2);

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
