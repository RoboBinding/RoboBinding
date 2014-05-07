package org.robobinding.viewattribute.listview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.listview.CheckedItemPositionsAttribute.MapCheckedItemPositionsAttribute;

import android.widget.ListView;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MapCheckedItemPositionsAttributeTest extends AbstractCheckedItemPositionsAttributeTest<ListView, MapCheckedItemPositionsAttribute> {
    private Map<Integer, Boolean> checkedItemPositions;

    @Before
    public void setUp() {
	super.setUp();

	checkedItemPositions = SparseBooleanArrayUtils.toMap(anySparseBooleanArray());
    }

    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	attribute.valueModelUpdated(checkedItemPositions);

	assertMapEquals(checkedItemPositions, SparseBooleanArrayUtils.toMap(view.getCheckedItemPositions()));
    }

    @Test
    public void whenCheckedItemPositionChanged_thenValueModelUpdatedAccordingly() {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ValueModel<Map<Integer, Boolean>> valueModel = (ValueModel) twoWayBindToProperty(Map.class, Maps.newHashMap());

	setItemsChecked(toSet(checkedItemPositions));

	assertMapEquals(checkedItemPositions, valueModel.getValue());
    }

    protected void assertMapEquals(Map<Integer, Boolean> expected, Map<Integer, Boolean> actual) {
	Set<Integer> expectedSet = toSet(expected);
	Set<Integer> actualSet = toSet(actual);
	assertThat(actualSet, equalTo(expectedSet));
    }

    protected Set<Integer> toSet(Map<Integer, Boolean> map) {
	Set<Integer> trueSet = Sets.newHashSet();
	for (Integer key : map.keySet()) {
	    if (map.get(key)) {
		trueSet.add(key);
	    }
	}
	return trueSet;
    }
}