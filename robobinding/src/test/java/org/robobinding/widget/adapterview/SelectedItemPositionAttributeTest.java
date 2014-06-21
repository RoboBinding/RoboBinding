package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeWithViewListenersAwareTest;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SelectedItemPositionAttributeTest extends
	AbstractPropertyViewAttributeWithViewListenersAwareTest<ListView, SelectedItemPositionAttribute, MockAdapterViewListeners> {
    private ArrayAdapter<String> arrayAdapter;

    @Before
    public void setUp() {
	arrayAdapter = new MockArrayAdapter();
	view.setAdapter(arrayAdapter);
    }

    @Test
    public void whenUpdatView_thenSelectedItemShouldBeUpdated() {
	int index = RandomValues.anyIndex(arrayAdapter.getCount());

	attribute.updateView(view, index);

	assertThat(view.getSelectedItemPosition(), is(index));
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChangeFromView() {
	int index = RandomValues.anyIndex(arrayAdapter.getCount());
	ValueModel<Integer> valueModel = ValueModelUtils.create();
	attribute.observeChangesOnTheView(view, valueModel);

	view.setSelection(index);

	assertThat(valueModel.getValue(), is(index));
    }

    @Test
    public void whenAllItemsAreRemovedFromAdapter_thenSelectedItemPositionShouldEqualInvalidPosition() {
	ValueModel<Integer> valueModel = ValueModelUtils.create();
	attribute.observeChangesOnTheView(view, valueModel);

	arrayAdapter.clear();
	arrayAdapter.notifyDataSetChanged();

	assertThat(valueModel.getValue(), is(AdapterView.INVALID_POSITION));
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	attribute.observeChangesOnTheView(view, null);
	assertTrue(viewListeners.addOnItemSelectedListenerInvoked);
    }
}
