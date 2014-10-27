package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.util.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.ValueModelUtils;
import org.robobinding.widget.adapterview.AbstractAdapterViewAttributeTest;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class CheckedItemPositionAttributeTest extends AbstractAdapterViewAttributeTest {
	private int checkedItemPosition;

	@Before
	public void setUpAdapter() {
		ListAdapter adapter = new SingleChoiceAdapter(Robolectric.application);
		view.setAdapter(adapter);
		view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		checkedItemPosition = nextInt(adapter.getCount());
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		CheckedItemPositionAttribute attribute = new CheckedItemPositionAttribute();
		attribute.updateView(view, checkedItemPosition);

		assertThat(view.getCheckedItemPosition(), equalTo(checkedItemPosition));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		CheckedItemPositionAttribute attribute = withListenersSet(new CheckedItemPositionAttribute());
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
		CheckedItemPositionAttribute attribute = withListenersSet(new CheckedItemPositionAttribute());
		attribute.observeChangesOnTheView(view, null);

		assertTrue(viewListeners.addOnItemClickListenerInvoked);
	}
}