package org.robobinding.viewattribute.listview;

import java.util.Set;

import org.junit.Before;
import org.robobinding.R;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.adapterview.MockAdapterViewListeners;
import org.robobinding.viewattribute.adapterview.MockArrayAdapter;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.util.SparseBooleanArray;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractCheckedItemPositionsAttributeTest<ViewType extends ListView, 
	PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType>>
	extends AbstractPropertyViewAttributeTest<ViewType, PropertyViewAttributeType> {
    private ListAdapter adapter;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
	super.initializeViewAndAttribute();

	adapter = new MockArrayAdapter(R.layout.simple_list_item_multiple_choice);
	view.setAdapter(adapter);
	view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	((ViewListenersAware<AdapterViewListeners>) attribute).setViewListeners(new MockAdapterViewListeners(view));
	preInitializeCheckedItemPositionsToRandomState();
    }

    private void preInitializeCheckedItemPositionsToRandomState() {
	setItemsChecked(SparseBooleanArrayUtils.toSet(anySparseBooleanArray()));
    }

    protected void setItemsChecked(Set<Integer> checkedItemPositions) {
	ListViewUtils.clearSelections(view);
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
		System.out.print(""); //Doing nothing.
	    }
	}
	return sparseBooleanArray;
    }
}
