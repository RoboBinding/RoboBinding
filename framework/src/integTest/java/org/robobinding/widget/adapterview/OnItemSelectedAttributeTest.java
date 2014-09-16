package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.EventCommand;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.widget.AdapterView;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class OnItemSelectedAttributeTest extends AbstractAdapterViewAttributeTest {
	private OnItemSelectedAttribute attribute;
	private EventCommand eventCommand;
	private int indexToSelect;
	private MockArrayAdapter arrayAdapter;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnItemSelectedAttribute());
		eventCommand = new EventCommand();
		
		arrayAdapter = new MockArrayAdapter(Robolectric.application);
		view.setAdapter(arrayAdapter);
		
		indexToSelect = RandomValues.anyIndex(arrayAdapter.getCount());
	}

	@Test
	public void givenBoundAttribute_whenSelectingAnItem_thenEventReceived() {
		attribute.bind(view, eventCommand);

		selectAnItem();

		assertEventReceived();
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnItemSelectedListenerInvoked);
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
		assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
	}

	@Test
	public void whenAllItemsAreRemovedFromAdapter_thenInvokeCommandPassingClickEventWithPositionAsInvalidPosition() {
		attribute.bind(view, eventCommand);

		arrayAdapter.clear();
		arrayAdapter.notifyDataSetChanged();

		eventCommand.assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = eventCommand.getEventReceived();
		assertThat(itemClickEvent.getPosition(),
				is(AdapterView.INVALID_POSITION));
	}

	@Test
	public void whenAdapterDataSetIsChanged_andSelectedItemPositionHasNotChanged_thenInvokeEvent() {
		attribute.bind(view, eventCommand);

		arrayAdapter.notifyDataSetChanged();

		assertEventReceivedWithIndex(view.getSelectedItemPosition());
	}

	@Test
	public void whenAdapterDataSetIsChanged_andSelectedItemPositionHasChanged_thenOnlyInvokeEventOnce() {
		attribute.bind(view, eventCommand);

		selectLastItem();
		arrayAdapter.removeLastElement();
		arrayAdapter.notifyDataSetChanged();

		eventCommand.assertTimesOfEventReceived(1);
	}

	private void selectLastItem() {
		view.setSelection(view.getCount() - 1);
	}
}
