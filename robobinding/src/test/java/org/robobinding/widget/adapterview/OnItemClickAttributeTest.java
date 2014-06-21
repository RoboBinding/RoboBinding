package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.event.AbstractEventViewAttributeWithViewListenersAwareTest;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemClickAttributeTest extends
	AbstractEventViewAttributeWithViewListenersAwareTest<ListView, OnItemClickAttribute, MockAdapterViewListeners> {
    private int indexToClick;

    @Before
    public void setUp() {
	ArrayAdapter<String> arrayAdapter = new MockArrayAdapter();
	view.setAdapter(arrayAdapter);
	indexToClick = RandomValues.anyIndex(arrayAdapter.getCount());
    }

    @Test
    public void givenBoundAttribute_whenClickingOnAnItem_thenEventReceived() {
	bindAttribute();

	clickOnAnItem();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	bindAttribute();

	assertTrue(viewListeners.addOnItemClickListenerInvoked);
    }

    private void clickOnAnItem() {
	ShadowListView shadowListView = Robolectric.shadowOf(view);
	shadowListView.performItemClick(indexToClick);
    }

    private void assertEventReceived() {
	assertEventReceived(ItemClickEvent.class);
	ItemClickEvent itemClickEvent = getEventReceived();
	assertTrue(itemClickEvent.getParent() == view);
	assertThat(itemClickEvent.getPosition(), is(indexToClick));
	assertThat(itemClickEvent.getView(), instanceOf(TextView.class));
    }
}
