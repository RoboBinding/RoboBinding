package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;
import org.robobinding.widget.listview.SparseBooleanArrayUtils;

import android.util.SparseBooleanArray;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SparseBooleanArrayCheckedItemPositionsAttributeTest extends
		AbstractCheckedItemPositionsAttributeTest<ListView, SparseBooleanArrayCheckedItemPositionsAttribute> {
    private SparseBooleanArray checkedItemPositions;

    @Before
    public void setUp() {
	checkedItemPositions = anySparseBooleanArray();
    }

    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	attribute.updateView(view, checkedItemPositions);

	assertSparseBooleanArrayEquals(checkedItemPositions, view.getCheckedItemPositions());
    }

    @Test
    public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
	SparseBooleanArray emptySparseBooleanArray = new SparseBooleanArray();
	ValueModel<SparseBooleanArray> valueModel = ValueModelUtils.create(emptySparseBooleanArray);
	attribute.observeChangesOnTheView(view, valueModel);

	setItemsChecked(SparseBooleanArrayUtils.toSet(checkedItemPositions));

	assertSparseBooleanArrayEquals(checkedItemPositions, valueModel.getValue());
    }

    private void assertSparseBooleanArrayEquals(SparseBooleanArray expected, SparseBooleanArray actual) {
	Set<Integer> expectedSet = SparseBooleanArrayUtils.toSet(expected);
	Set<Integer> actualSet = SparseBooleanArrayUtils.toSet(actual);
	assertThat(actualSet, equalTo(expectedSet));
    }
}
