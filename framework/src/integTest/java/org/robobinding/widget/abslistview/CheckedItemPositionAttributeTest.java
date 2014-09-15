package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.util.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.widget.AbstractPropertyViewAttributeWithViewListenersAwareTest;
import org.robobinding.widget.adapterview.MockAdapterViewListeners;
import org.robolectric.annotation.Config;

import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class CheckedItemPositionAttributeTest
		extends
		AbstractPropertyViewAttributeWithViewListenersAwareTest<ListView, CheckedItemPositionAttribute, MockAdapterViewListeners> {
	private int checkedItemPosition;

	@Before
	public void setUp() {

		ListAdapter adapter = new SingleChoiceAdapter();
		view.setAdapter(adapter);
		view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		checkedItemPosition = nextInt(adapter.getCount());
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		attribute.updateView(view, checkedItemPosition);

		assertThat(view.getCheckedItemPosition(), equalTo(checkedItemPosition));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		ValueModel<Integer> valueModel = ValueModelUtils.create();
		attribute.observeChangesOnTheView(view, valueModel);

		setItemChecked();

		assertThat(valueModel.getValue(), equalTo(checkedItemPosition));
	}

	private void setItemChecked() {
		view.performItemClick(view, checkedItemPosition, 0);
	}

	@Test
	public void whenObserveChangesOnTheView_thenRegisterWithMulticastListener() {
		attribute.observeChangesOnTheView(view, null);

		assertTrue(viewListeners.addOnItemClickListenerInvoked);
	}
}