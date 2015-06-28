package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ValueModelUtils;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.MapCheckedItemPositionsAttribute;
import org.robolectric.annotation.Config;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class MapCheckedItemPositionsAttributeTest extends AbstractCheckedItemPositionsAttributeTest {
	private MapCheckedItemPositionsAttribute attribute;
	private Map<Integer, Boolean> checkedItemPositions;

	@Before
	public void setUp() {
		attribute = new MapCheckedItemPositionsAttribute();
		
		checkedItemPositions = SparseBooleanArrayUtils.toMap(anySparseBooleanArray());
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		attribute.updateView(view, checkedItemPositions, viewAddOn);

		assertMapEquals(checkedItemPositions, SparseBooleanArrayUtils.toMap(view.getCheckedItemPositions()));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		Map<Integer, Boolean> emptyMap = Maps.newHashMap();
		ValueModel<Map<Integer, Boolean>> valueModel = ValueModelUtils.create(emptyMap);
		attribute.observeChangesOnTheView(viewAddOn, valueModel, view);

		setItemsChecked(toSet(checkedItemPositions));

		assertMapEquals(checkedItemPositions, valueModel.getValue());
	}

	private void assertMapEquals(Map<Integer, Boolean> expected, Map<Integer, Boolean> actual) {
		Set<Integer> expectedSet = toSet(expected);
		Set<Integer> actualSet = toSet(actual);
		assertThat(actualSet, equalTo(expectedSet));
	}

	private Set<Integer> toSet(Map<Integer, Boolean> map) {
		Set<Integer> trueSet = Sets.newHashSet();
		for (Integer key : map.keySet()) {
			if (map.get(key)) {
				trueSet.add(key);
			}
		}
		return trueSet;
	}
}