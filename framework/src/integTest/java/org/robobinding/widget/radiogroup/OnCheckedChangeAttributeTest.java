package org.robobinding.widget.radiogroup;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.EventCommand;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class OnCheckedChangeAttributeTest extends AbstractRadioGroupAttributeTest {
	private OnCheckedChangeAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = new OnCheckedChangeAttribute();
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
		bindAttribute();

		changeCheckedId();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void changeCheckedId() {
		view.check(RandomValues.anyIntegerGreaterThanZero());
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(CheckedChangeEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewAddOn.addOnCheckedChangeListenerInvoked);
	}
}