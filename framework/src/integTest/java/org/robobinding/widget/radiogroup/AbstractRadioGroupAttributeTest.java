package org.robobinding.widget.radiogroup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.widget.RadioGroup;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractRadioGroupAttributeTest {
	protected RadioGroup view;
	protected MockRadioGroupAddOn viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new RadioGroup(RuntimeEnvironment.application);
		viewAddOn = new MockRadioGroupAddOn(view);
	}
}
