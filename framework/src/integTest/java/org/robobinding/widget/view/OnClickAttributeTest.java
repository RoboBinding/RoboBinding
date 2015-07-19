package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.widget.EventCommand;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class OnClickAttributeTest extends AbstractViewEventAttributeTest {
	private OnClickAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = new OnClickAttribute();
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenClickingOnView_thenEventReceived() {
		bindAttribute();

		clickOnView();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void clickOnView() {
		view.performClick();
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ClickEvent.class);
		ClickEvent clickEvent = eventCommand.getEventReceived();
		assertTrue(clickEvent.getView() == view);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewAddOn.addOnClickListenerInvoked);
	}
}
