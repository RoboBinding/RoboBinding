package org.robobinding.widget.abslistview;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractAbsListViewAttributeTest {
	protected AbsListView view;
	protected MockAbsListViewAddOn viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new ListView(RuntimeEnvironment.application);
		viewAddOn = new MockAbsListViewAddOn(view);
	}
}
