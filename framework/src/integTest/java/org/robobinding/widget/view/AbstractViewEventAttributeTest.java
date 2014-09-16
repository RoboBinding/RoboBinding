package org.robobinding.widget.view;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.view.View;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractViewEventAttributeTest {
	protected View view;
	protected MockViewListenersForView viewListeners;
	
	@Before
	public void initializeViewAndListeners() {
		view = new View(Robolectric.application);
		viewListeners = new MockViewListenersForView(view);
	}
	
	public <T extends ViewListenersAware<ViewListenersForView>> T withListenersSet(T attribute) {
		attribute.setViewListeners(viewListeners);
		return attribute;
	}
}
