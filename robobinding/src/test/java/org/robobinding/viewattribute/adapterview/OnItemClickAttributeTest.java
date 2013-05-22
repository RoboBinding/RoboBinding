/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

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
	AbstractCommandViewAttributeWithViewListenersAwareTest<ListView, OnItemClickAttribute, MockAdapterViewListeners> {
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
