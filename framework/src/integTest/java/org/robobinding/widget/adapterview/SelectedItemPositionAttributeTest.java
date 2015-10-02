package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class SelectedItemPositionAttributeTest extends AbstractAdapterViewAttributeTest {
	private SelectedItemPositionAttribute attribute;
	private ArrayAdapter<String> arrayAdapter;

	@Before
	public void setUp() {
		attribute = new SelectedItemPositionAttribute();

		arrayAdapter = new MockArrayAdapter(RuntimeEnvironment.application);
		view.setAdapter(arrayAdapter);
	}

	@Test
	public void whenUpdatView_thenSelectedItemShouldBeUpdated() {
		int index = RandomValues.anyIndex(arrayAdapter.getCount());

		attribute.updateView(view, index, null);

		assertThat(view.getSelectedItemPosition(), is(index));
	}

	/*
	 * TODO: ListView.setSelection(position) not working in Robolectric 2.x yet,
	 * see org.robolectric.shadows.ListViewTest.
	 * testSetSelection_ShouldFireOnItemSelectedListener().
	 */
	@Ignore
	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChangeFromView() {
		int index = RandomValues.anyIndex(arrayAdapter.getCount());
		ValueModel<Integer> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

		view.setSelection(index);

		assertThat(valueModel.getValue(), is(index));
	}

	/**
	 * TODO:Looks an unnecessary test. Will check later time.
	 */
	@Ignore
	@Test
	public void whenAllItemsAreRemovedFromAdapter_thenSelectedItemPositionShouldEqualInvalidPosition() {
		ValueModel<Integer> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

		arrayAdapter.clear();
		// arrayAdapter.notifyDataSetChanged();

		assertThat(valueModel.getValue(), is(AdapterView.INVALID_POSITION));
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener() {
		attribute.observeChangesOnTheView(viewAddOn, null, view);
		assertTrue(viewAddOn.addOnItemSelectedListenerInvoked);
	}
}
