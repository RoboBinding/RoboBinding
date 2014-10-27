package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.util.RandomValues;
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
public class OnFocusChangeAttributeTest extends AbstractViewEventAttributeTest {
	private OnFocusChangeAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnFocusChangeAttribute());
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenChangeFocus_thenEventReceived() {
		attribute.bind(view, eventCommand);

		changeViewFocus();

		assertEventReceived();
	}

	private void changeViewFocus() {
		ShadowView shadowView = Robolectric.shadowOf(view);
		shadowView.setViewFocus(RandomValues.trueOrFalse());
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
