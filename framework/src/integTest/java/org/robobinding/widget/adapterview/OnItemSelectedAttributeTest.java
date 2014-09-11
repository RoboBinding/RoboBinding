package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemSelectedAttributeTest extends
	AbstractEventViewAttributeWithViewListenersAwareTest<ListView, OnItemSelectedAttribute, MockAdapterViewListeners> {
    private int indexToSelect;
    private MockArrayAdapter arrayAdapter;

    @Before
    public void setUp() {
	arrayAdapter = new MockArrayAdapter();
	view.setAdapter(arrayAdapter);
	indexToSelect = RandomValues.anyIndex(arrayAdapter.getCount());
    }

    @Test
    public void givenBoundAttribute_whenSelectingAnItem_thenEventReceived() {
	bindAttribute();

	selectAnItem();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	bindAttribute();

	assertTrue(viewListeners.addOnItemSelectedListenerInvoked);
    }

    private void selectAnItem() {
	view.setSelection(indexToSelect);
    }

    private void assertEventReceived() {
	assertEventReceivedWithIndex(indexToSelect);
    }

    private void assertEventReceivedWithIndex(int index) {
	assertEventReceived(ItemClickEvent.class);
	ItemClickEvent itemClickEvent = getEventReceived();
	assertTrue(itemClickEvent.getParent() == view);
	assertThat(itemClickEvent.getPosition(), is(index));
	assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
    }

    @Test
    public void whenAllItemsAreRemovedFromAdapter_thenInvokeCommandPassingClickEventWithPositionAsInvalidPosition() {
	bindAttribute();

	arrayAdapter.clear();
	arrayAdapter.notifyDataSetChanged();

	assertEventReceived(ItemClickEvent.class);
	ItemClickEvent itemClickEvent = getEventReceived();
	assertThat(itemClickEvent.getPosition(), is(AdapterView.INVALID_POSITION));
    }

    @Test
    public void whenAdapterDataSetIsChanged_andSelectedItemPositionHasNotChanged_thenInvokeEvent() {
	bindAttribute();

	arrayAdapter.notifyDataSetChanged();

	assertEventReceivedWithIndex(view.getSelectedItemPosition());
    }

    @Test
    public void whenAdapterDataSetIsChanged_andSelectedItemPositionHasChanged_thenOnlyInvokeEventOnce() {
	bindAttribute();

	selectLastItem();
	arrayAdapter.removeLastElement();
	arrayAdapter.notifyDataSetChanged();

	assertTimesOfEventReceived(1);
    }

    private void selectLastItem() {
	view.setSelection(view.getCount() - 1);
    }
}
