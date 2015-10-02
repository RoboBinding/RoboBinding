package org.robobinding.widget.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.view.VisibilityAttribute.BooleanVisibilityAttribute;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BooleanVisibilityAttributeTest {
	private AbstractVisibility visibility;
	private View view;
	private BooleanVisibilityAttribute<View> attribute;

	@Before
	public void setUp() {
		view = new View(RuntimeEnvironment.application);
		visibility = mock(AbstractVisibility.class);
		attribute = new BooleanVisibilityAttribute<View>(visibility);
	}

	@Test
	public void whenUpdateToVisible_thenMakeVisibleIsCalled() {
		boolean visible = true;

		attribute.updateView(view, visible);

		verify(visibility).makeVisible();
	}

	@Test
	public void whenUpdateToGone_thenMakeGoneIsCalled() {
		boolean gone = false;

		attribute.updateView(view, gone);

		verify(visibility).makeGone();
	}
}
