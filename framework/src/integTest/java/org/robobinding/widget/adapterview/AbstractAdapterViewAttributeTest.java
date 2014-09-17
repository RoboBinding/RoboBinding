package org.robobinding.widget.adapterview;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.widget.ListView;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractAdapterViewAttributeTest {
	protected ListView view;
	protected MockAdapterViewListeners viewListeners;

	@Before
	public void initializeViewAndListeners() {
		view = new ListView(Robolectric.application);
		viewListeners = new MockAdapterViewListeners(view);
	}

	public <T extends ViewListenersAware<AdapterViewListeners>> T withListenersSet(T attribute) {
		attribute.setViewListeners(viewListeners);
		return attribute;
	}
}
