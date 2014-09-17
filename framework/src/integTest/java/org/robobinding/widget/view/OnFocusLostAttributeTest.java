package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.widget.EventCommand;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class OnFocusLostAttributeTest extends AbstractViewEventAttributeTest {
	private OnFocusLostAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnFocusLostAttribute());
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenClearFocus_thenEventReceived() {
		attribute.bind(view, eventCommand);

		clearViewFocus();

		assertEventReceived();
	}

	private void clearViewFocus() {
		ShadowView shadowView = Robolectric.shadowOf(view);
		shadowView.setViewFocus(false);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(AbstractViewEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnFocusChangeListenerInvoked);
	}
}