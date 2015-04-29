package org.robobinding.widget.abslistview;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.widget.EventCommand;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class OnScrollAttributeTest extends AbstractAbsListViewAttributeTest {
	private OnScrollAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnScrollAttribute());
		eventCommand = new EventCommand();
	}

	/**
	 * TODO: Not supported by Robolectric 2.x yet.
	 */
	@Ignore
	@Test
	public void givenBoundAttribute_whenScrollView_thenEventReceived() {
		attribute.bind(view, eventCommand);

		scrollView();

		assertEventReceived();
	}

	private void scrollView() {
		view.smoothScrollToPositionFromTop(1, 0, 500);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ScrollEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnScrollListenerInvoked);
	}

}