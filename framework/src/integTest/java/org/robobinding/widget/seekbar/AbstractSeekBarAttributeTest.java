package org.robobinding.widget.seekbar;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersAware;
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
	protected MockSeekBarListeners viewListeners;

	@Before
	public void initializeViewAndListeners() {
		view = new SeekBar(Robolectric.application);
		viewListeners = new MockSeekBarListeners(view);
	}

	public <T extends ViewListenersAware<SeekBarListeners>> T withListenersSet(T attribute) {
		attribute.setViewListeners(viewListeners);
		return attribute;
	}
}
