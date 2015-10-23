package org.robobinding.widget.seekbar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.widget.SeekBar;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractSeekBarAttributeTest {
	protected SeekBar view;
	protected MockSeekBarAddOn viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new SeekBar(RuntimeEnvironment.application);
		viewAddOn = new MockSeekBarAddOn(view);
	}
}
