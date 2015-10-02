package org.robobinding.widget.abslistview;

import java.util.Set;

import org.junit.Before;
import org.robobinding.util.RandomValues;
import org.robolectric.RuntimeEnvironment;

import android.util.SparseBooleanArray;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractCheckedItemPositionsAttributeTest extends AbstractAbsListViewAttributeTest {
	private ListAdapter adapter;

	@Before
	public void initializeAdapterAndViewListeners() {
		adapter = new MultipleChoiceAdapter(RuntimeEnvironment.application);
		view.setAdapter(adapter);
		view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

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
