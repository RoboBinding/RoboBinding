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
import org.robolectric.shadows.ShadowListView;

import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class OnItemClickAttributeTest extends AbstractAdapterViewAttributeTest {
	private OnItemClickAttribute attribute;
	private EventCommand eventCommand;
	private int indexToClick;

	@Before
	public void setUp() {
		attribute = withListenersSet(new OnItemClickAttribute());
		eventCommand = new EventCommand();

		ArrayAdapter<String> arrayAdapter = new MockArrayAdapter(Robolectric.application);
		view.setAdapter(arrayAdapter);
		
		indexToClick = RandomValues.anyIndex(arrayAdapter.getCount());
	}

	@Test
	public void givenBoundAttribute_whenClickingOnAnItem_thenEventReceived() {
		attribute.bind(view, eventCommand);

		clickOnAnItem();

		assertEventReceived();
	}

	private void clickOnAnItem() {
		ShadowListView shadowListView = Robolectric.shadowOf(view);
		shadowListView.performItemClick(indexToClick);
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(ItemClickEvent.class);
		ItemClickEvent itemClickEvent = eventCommand.getEventReceived();
		assertTrue(itemClickEvent.getParent() == view);
		assertThat(itemClickEvent.getPosition(), is(indexToClick));
		assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener() {
		attribute.bind(view, eventCommand);

		assertTrue(viewListeners.addOnItemClickListenerInvoked);
	}
}
