package org.robobinding.widget.listview;

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
@Config(manifest=Config.NONE)
public class OnScrollStateChangedAttributeTest extends AbstractListViewAttributeTest {
	private OnScrollStateChangedAttribute attribute;
	private EventCommand eventCommand;
	
	@Before
	public void setUp() {
		attribute = withListenersSet(new OnScrollStateChangedAttribute());
		eventCommand = new EventCommand();
	}
	
	@Test
	@Ignore
	public void givenBoundAttribute_whenChangeScrollState_thenEventReceived() {
		attribute.bind(view, eventCommand);

		changeScrollState();

		assertEventReceived();
	}

	private void changeScrollState() {
		view.smoothScrollToPositionFromTop(1, 0, 500);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ScrollStateChangedEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnScrollListenerInvoked);
	}

}
