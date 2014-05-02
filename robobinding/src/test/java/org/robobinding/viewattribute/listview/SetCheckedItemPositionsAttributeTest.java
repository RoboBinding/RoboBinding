package org.robobinding.viewattribute.listview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.SetCheckedItemPositionsAttribute;

import android.widget.ListView;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SetCheckedItemPositionsAttributeTest extends AbstractCheckedItemPositionsAttributeTest<ListView, SetCheckedItemPositionsAttribute> {
    private Set<Integer> checkedItemPositions;

    @Before
    public void setUp() {
	super.setUp();

	checkedItemPositions = SparseBooleanArrayUtils.toSet(anySparseBooleanArray());
    }

    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	attribute.valueModelUpdated(checkedItemPositions);

	assertThat(SparseBooleanArrayUtils.toSet(view.getCheckedItemPositions()), equalTo(checkedItemPositions));
    }

    @Test
    public void whenCheckedItemPositionChanged_thenValueModelUpdatedAccordingly() {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ValueModel<Set<Integer>> valueModel = (ValueModel) twoWayBindToProperty(Set.class, Sets.newHashSet());

	setItemsChecked(checkedItemPositions);

	assertThat(valueModel.getValue(), equalTo(checkedItemPositions));
    }

}