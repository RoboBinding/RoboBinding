package org.robobinding.widget.compoundbutton;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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
		view = new CheckBox(Robolectric.application);
		viewAddOn = new MockCompoundButtonAddOn(view);
	}
}
