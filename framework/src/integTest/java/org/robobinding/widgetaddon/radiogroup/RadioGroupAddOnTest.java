package org.robobinding.widgetaddon.radiogroup;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class RadioGroupAddOnTest {
	@Test
	public void shouldSupportMultipleOnCheckedChangeListeners() {
		RadioGroup view = new RadioGroup(RuntimeEnvironment.application);
		RadioGroupAddOn viewAddOn = new RadioGroupAddOn(view);

		MockOnCheckedChangeListener listener1 = new MockOnCheckedChangeListener();
		MockOnCheckedChangeListener listener2 = new MockOnCheckedChangeListener();

		viewAddOn.addOnCheckedChangeListener(listener1);
		viewAddOn.addOnCheckedChangeListener(listener2);

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