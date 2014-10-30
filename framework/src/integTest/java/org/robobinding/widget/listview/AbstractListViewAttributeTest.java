package org.robobinding.widget.listview;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.widgetaddon.ViewAddOnAware;
import org.robobinding.widgetaddon.listview.ListViewListeners;
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
public abstract class AbstractListViewAttributeTest {
	protected ListView view;
	protected MockListViewListeners viewListeners;

	@Before
	public void initializeViewAndListeners() {
		view = new ListView(Robolectric.application);
		viewListeners = new MockListViewListeners(view);
	}

	public <T extends ViewAddOnAware<ListViewListeners>> T withListenersSet(T attribute) {
		attribute.setViewAddOn(viewListeners);
		return attribute;
	}
}
