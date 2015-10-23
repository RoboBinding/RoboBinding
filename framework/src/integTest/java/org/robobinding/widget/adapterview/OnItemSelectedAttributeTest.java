package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.EventCommand;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class OnItemSelectedAttributeTest extends AbstractAdapterViewAttributeTest {
	private OnItemSelectedAttribute attribute;
	private EventCommand eventCommand;
	private int indexToSelect;
	private MockArrayAdapter adapter;

	@Before
	public void setUp() {
		attribute = new OnItemSelectedAttribute();
		eventCommand = new EventCommand();

		adapter = new MockArrayAdapter(RuntimeEnvironment.application);
		view.setAdapter(adapter);

		indexToSelect = RandomValues.anyIndex(adapter.getCount());
	}

	/*
	 * TODO: ListView.setSelection(position) not working in Robolectric 2.x yet,
	 * see org.robolectric.shadows.ListViewTest.
	 * testSetSelection_ShouldFireOnItemSelectedListener().
	 */
	@Ignore
	@Test
	public void givenBoundAttribute_whenSelectingAnItem_thenEventReceived() {
		bindAttribute();

		selectAnItem();

		assertEventReceived();
	}

	private void bindAttribute() {
		attribute.bind(viewAddOn, eventCommand, view);
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener() {
		bindAttribute();

		assertTrue(viewAddOn.addOnItemSelectedListenerInvoked);
	}

	private void selectAnItem() {
		view.setSelection(indexToSelect);
	}

	private void assertEventReceived() {
		assertEventReceivedWithIndex(indexToSelect);
	}

	private void assertEventReceivedWithIndex(int index) {
		eventCommand.assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = eventCommand.getEventReceived();
		assertTrue(itemClickEvent.getParent() == view);
		assertThat(itemClickEvent.getPosition(), is(index));
		// assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
	}

	/**
	 * TODO:Looks an unnecessary test. Will check later time.
	 */
	@Ignore
	@Test
	public void whenAllItemsAreRemovedFromAdapter_thenInvokeCommandPassingClickEventWithPositionAsInvalidPosition() {
		bindAttribute();

		adapter.clear();

		eventCommand.assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = eventCommand.getEventReceived();
		assertThat(itemClickEvent.getPosition(), is(AdapterView.INVALID_POSITION));
	}

	/**
	 * TODO:Looks an unnecessary test. Will check later time.
	 */
	@Ignore
	@Test
	public void whenAdapterDataSetIsChanged_andSelectedItemPositionHasNotChanged_thenInvokeEvent() {
		bindAttribute();

		adapter.notifyDataSetChanged();

		assertEventReceivedWithIndex(view.getSelectedItemPosition());
	}

	/*
	 * TODO: ListView.setSelection(position) not working in Robolectric 2.x yet,
	 * see org.robolectric.shadows.ListViewTest.
	 * testSetSelection_ShouldFireOnItemSelectedListener().
	 */
	@Ignore
	@Test
	public void whenAdapterDataSetIsChanged_andSelectedItemPositionHasChanged_thenOnlyInvokeEventOnce() {
		bindAttribute();

		selectLastItem();
		adapter.removeLastItem();
		adapter.notifyDataSetChanged();

		eventCommand.assertTimesOfEventReceived(1);
	}

	private void selectLastItem() {
		view.setSelection(view.getCount() - 1);
	}
}
