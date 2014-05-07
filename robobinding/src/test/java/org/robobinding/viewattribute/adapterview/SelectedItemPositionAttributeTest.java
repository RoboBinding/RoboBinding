package org.robobinding.viewattribute.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractPropertyViewAttributeWithViewListenersAwareTest;

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
    private ValueModel<Integer> valueModel;

    @Before
    public void setUp() {
	valueModel = twoWayBindToProperty(Integer.class);
	arrayAdapter = new MockArrayAdapter();
	view.setAdapter(arrayAdapter);
    }

    @Test
    public void whenUpdatingValueModel_thenSelectedItemShouldBeUpdated() {
	int index = RandomValues.anyIndex(arrayAdapter.getCount());

	valueModel.setValue(index);

	assertThat(view.getSelectedItemPosition(), is(index));
    }

    @Test
    public void whenUpdatingSelectedItem_thenPresentationModelShouldBeUpdated() {
	int index = RandomValues.anyIndex(arrayAdapter.getCount());

	view.setSelection(index);

	assertThat(valueModel.getValue(), is(index));
    }

    @Test
    public void whenAllItemsAreRemovedFromAdapter_thenSelectedItemPositionShouldEqualInvalidPosition() {
	arrayAdapter.clear();

	arrayAdapter.notifyDataSetChanged();

	assertThat(valueModel.getValue(), is(AdapterView.INVALID_POSITION));
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	assertTrue(viewListeners.addOnItemSelectedListenerInvoked);
    }
}
