package org.robobinding.widget.compoundbutton;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersAware;
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
	protected MockCompoundButtonListeners viewListeners;
	
	@Before
	public void initializeViewAndListeners() {
		view = new CheckBox(Robolectric.application);
		viewListeners = new MockCompoundButtonListeners(view);
	}
	
	public <T extends ViewListenersAware<CompoundButtonListeners>> T withListenersSet(T attribute) {
		attribute.setViewListeners(viewListeners);
		return attribute;
	}
}
