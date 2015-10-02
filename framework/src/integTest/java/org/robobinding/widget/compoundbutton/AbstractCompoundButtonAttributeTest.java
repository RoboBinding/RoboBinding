package org.robobinding.widget.compoundbutton;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractCompoundButtonAttributeTest {
	protected CompoundButton view;
	protected MockCompoundButtonAddOn viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new CheckBox(RuntimeEnvironment.application);
		viewAddOn = new MockCompoundButtonAddOn(view);
	}
}
