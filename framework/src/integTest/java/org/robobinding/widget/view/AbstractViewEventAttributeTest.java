package org.robobinding.widget.view;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

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
	protected MockViewAddOnForView viewAddOn;

	@Before
	public void initializeViewAndListeners() {
		view = new View(RuntimeEnvironment.application);
		viewAddOn = new MockViewAddOnForView(view);
	}
}
