package org.robobinding.widget.seekbar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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
		view = new SeekBar(Robolectric.application);
		viewAddOn = new MockSeekBarAddOn(view);
	}
}
