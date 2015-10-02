package org.robobinding.widget.view;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.widget.EventCommand;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class OnFocusAttributeTest extends AbstractViewEventAttributeTest {
	private OnFocusAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = new OnFocusAttribute();
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenApplyFocus_thenEventReceived() {
		bindAttribute();

		setViewFocus();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void setViewFocus() {
		ShadowView shadowView = Shadows.shadowOf(view);
		shadowView.setViewFocus(true);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(AbstractViewEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewAddOn.addOnFocusChangeListenerInvoked);
	}

}
