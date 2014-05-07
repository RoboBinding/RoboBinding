package org.robobinding.viewattribute.listview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;

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
	super.setUp();

	checkedItemPositions = anySparseBooleanArray();
    }

    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	attribute.valueModelUpdated(checkedItemPositions);

	assertSparseBooleanArrayEquals(checkedItemPositions, view.getCheckedItemPositions());
    }

    @Test
    public void whenCheckedItemPositionChanged_thenValueModelUpdatedAccordingly() {
	ValueModel<SparseBooleanArray> valueModel = twoWayBindToProperty(SparseBooleanArray.class, new SparseBooleanArray());

	setItemsChecked(SparseBooleanArrayUtils.toSet(checkedItemPositions));

	assertSparseBooleanArrayEquals(checkedItemPositions, valueModel.getValue());
    }

    private void assertSparseBooleanArrayEquals(SparseBooleanArray expected, SparseBooleanArray actual) {
	Set<Integer> expectedSet = SparseBooleanArrayUtils.toSet(expected);
	Set<Integer> actualSet = SparseBooleanArrayUtils.toSet(actual);
	assertThat(actualSet, equalTo(expectedSet));
    }
}
