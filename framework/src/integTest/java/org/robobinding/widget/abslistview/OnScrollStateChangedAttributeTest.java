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
public class OnScrollStateChangedAttributeTest extends AbstractAbsListViewAttributeTest {
	private OnScrollStateChangedAttribute attribute;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		attribute = new OnScrollStateChangedAttribute();
		eventCommand = new EventCommand();
	}

	/**
	 * TODO: Not supported by Robolectric 2.x yet.
	 */
	@Ignore
	@Test
	public void givenBoundAttribute_whenChangeScrollState_thenEventReceived() {
		bindAttribute();

		changeScrollState();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void changeScrollState() {
		view.smoothScrollToPositionFromTop(1, 0, 500);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ScrollStateChangedEvent.class);
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners() {
		bindAttribute();

		assertTrue(viewAddOn.addOnScrollListenerInvoked);
	}

}
