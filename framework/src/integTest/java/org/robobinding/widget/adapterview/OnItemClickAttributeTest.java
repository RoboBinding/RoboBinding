package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.EventCommand;
import org.robobinding.widget.abslistview.SingleChoiceAdapter;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;

import android.widget.ListAdapter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class OnItemClickAttributeTest extends AbstractAdapterViewAttributeTest {
	private OnItemClickAttribute attribute;
	private EventCommand eventCommand;
	private int indexToClick;

	@Before
	public void setUp() {
		attribute = new OnItemClickAttribute();
		eventCommand = new EventCommand();

		ListAdapter arrayAdapter = new SingleChoiceAdapter(RuntimeEnvironment.application);
		view.setAdapter(arrayAdapter);

		indexToClick = RandomValues.anyIndex(arrayAdapter.getCount());
	}

	@Test
	public void givenBoundAttribute_whenClickingOnAnItem_thenEventReceived() {
		bindAttribute();

		clickOnAnItem();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	private void clickOnAnItem() {
		ShadowListView shadowListView = Shadows.shadowOf(view);
		shadowListView.performItemClick(indexToClick);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = eventCommand.getEventReceived();
		assertTrue(itemClickEvent.getParent() == view);
		assertThat(itemClickEvent.getPosition(), is(indexToClick));
		// assertNotNull(itemClickEvent.getView());
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener() {
		bindAttribute();

		assertTrue(viewAddOn.addOnItemClickListenerInvoked);
	}
}
