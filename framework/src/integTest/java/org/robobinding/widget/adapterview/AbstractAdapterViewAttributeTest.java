package org.robobinding.widget.adapterview;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.widgetaddon.ViewAddOnAware;
import org.robobinding.widgetaddon.adapterview.AdapterViewListeners;
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

	public <T extends ViewAddOnAware<AdapterViewListeners>> T withListenersSet(T attribute) {
		attribute.setViewAddOn(viewListeners);
		return attribute;
	}
}
