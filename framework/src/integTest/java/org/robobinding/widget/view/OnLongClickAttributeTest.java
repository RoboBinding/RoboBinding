package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.robolectric.DefaultTestRunner;
import org.robobinding.widget.EventCommand;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
//@Config(manifest=Config.NONE)
@RunWith(DefaultTestRunner.class)
public class OnLongClickAttributeTest extends AbstractViewEventAttributeTest {
	private OnLongClickAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnLongClickAttribute());
		eventCommand = new EventCommand();
	}
	
	@Test
	public void givenBoundAttribute_whenLongClickOnView_thenEventReceived() {
		attribute.bind(view, eventCommand);

		longClickOnView();

		assertEventReceived();
	}

	private void longClickOnView() {
		view.performLongClick();
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ClickEvent.class);
		ClickEvent clickEvent = eventCommand.getEventReceived();
		assertTrue(clickEvent.getView() == view);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnLongClickListenerInvoked);
	}
}
