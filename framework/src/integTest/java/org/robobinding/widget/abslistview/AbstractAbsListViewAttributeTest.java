package org.robobinding.widget.abslistview;

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
public abstract class AbstractAbsListViewAttributeTest {
	protected ListView view;
	protected MockAbsListViewListeners viewListeners;

	@Before
	public void initializeViewAndListeners() {
		view = new ListView(Robolectric.application);
		viewListeners = new MockAbsListViewListeners(view);
	}

	public <T extends ViewListenersAware<AbsListViewListeners>> T withListenersSet(T attribute) {
		attribute.setViewListeners(viewListeners);
		return attribute;
	}
}
