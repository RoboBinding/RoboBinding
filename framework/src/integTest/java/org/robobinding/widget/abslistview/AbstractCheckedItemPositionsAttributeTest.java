package org.robobinding.widget.abslistview;

import java.util.Set;

import org.junit.Before;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ViewListenersAware;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.adapterview.AdapterViewListeners;
import org.robobinding.widget.adapterview.MockAdapterViewListeners;
import org.robobinding.widget.listview.SparseBooleanArrayUtils;

import android.util.SparseBooleanArray;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractCheckedItemPositionsAttributeTest<ViewType extends ListView, PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType, ?>>
		extends AbstractPropertyViewAttributeTest<ViewType, PropertyViewAttributeType> {
	private ListAdapter adapter;

	@SuppressWarnings("unchecked")
	@Before
	public void initializeAdapterAndViewListeners() {

		/*adapter = new MockArrayAdapter(
				R.layout.simple_list_item_multiple_choice);*/
		adapter = new MultipleChoiceAdapter();
		view.setAdapter(adapter);
		view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		((ViewListenersAware<AdapterViewListeners>) attribute)
				.setViewListeners(new MockAdapterViewListeners(view));
		preInitializeCheckedItemPositionsToRandomState();
	}

	private void preInitializeCheckedItemPositionsToRandomState() {
		setItemsChecked(SparseBooleanArrayUtils.toSet(anySparseBooleanArray()));
	}

	protected void setItemsChecked(Set<Integer> checkedItemPositions) {
		view.clearChoices();
		for (Integer checkedItemPosition : checkedItemPositions) {
			view.performItemClick(null, checkedItemPosition, 0);
		}
	}

	protected SparseBooleanArray anySparseBooleanArray() {
		SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
		for (int i = 0; i < adapter.getCount(); i++) {
			int selector = RandomValues.nextInt(3);
			if (selector == 0) {
				sparseBooleanArray.put(i, true);
			} else if (selector == 1) {
				sparseBooleanArray.put(i, false);
			} else {
				System.out.print(""); // Doing nothing.
			}
		}
		return sparseBooleanArray;
	}
}
