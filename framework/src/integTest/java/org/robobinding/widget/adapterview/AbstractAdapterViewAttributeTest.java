package org.robobinding.widget.adapterview;

import org.junit.Before;
import org.junit.runner.RunWith;
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
	protected MockAdapterViewAddOn viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new ListView(Robolectric.application);
		viewAddOn = new MockAdapterViewAddOn(view);
	}
}
