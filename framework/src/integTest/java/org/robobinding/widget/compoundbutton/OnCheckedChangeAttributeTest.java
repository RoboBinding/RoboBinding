package org.robobinding.widget.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
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
public class OnCheckedChangeAttributeTest extends AbstractCompoundButtonAttributeTest {
	private OnCheckedChangeAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnCheckedChangeAttribute());
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
		attribute.bind(view, eventCommand);

		changeCheckedState();

		assertEventReceived();
	}

	private void changeCheckedState() {
		view.setChecked(!view.isChecked());
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(CheckedChangeEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
	}
}
