package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;
import org.robolectric.annotation.Config;

import android.util.SparseBooleanArray;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class SparseBooleanArrayCheckedItemPositionsAttributeTest extends AbstractCheckedItemPositionsAttributeTest {
	private SparseBooleanArray checkedItemPositions;

	@Before
	public void setUpTestData() {
		checkedItemPositions = anySparseBooleanArray();
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		SparseBooleanArrayCheckedItemPositionsAttribute attribute = new SparseBooleanArrayCheckedItemPositionsAttribute();
		attribute.updateView(view, checkedItemPositions);

		assertSparseBooleanArrayEquals(checkedItemPositions,
				view.getCheckedItemPositions());
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		SparseBooleanArrayCheckedItemPositionsAttribute attribute = withListenersSet(new SparseBooleanArrayCheckedItemPositionsAttribute());
		SparseBooleanArray emptySparseBooleanArray = new SparseBooleanArray();
		ValueModel<SparseBooleanArray> valueModel = ValueModelUtils.create(emptySparseBooleanArray);
		attribute.observeChangesOnTheView(view, valueModel);

		setItemsChecked(SparseBooleanArrayUtils.toSet(checkedItemPositions));

		assertSparseBooleanArrayEquals(checkedItemPositions, valueModel.getValue());
	}

	private void assertSparseBooleanArrayEquals(SparseBooleanArray expected,
			SparseBooleanArray actual) {
		Set<Integer> expectedSet = SparseBooleanArrayUtils.toSet(expected);
		Set<Integer> actualSet = SparseBooleanArrayUtils.toSet(actual);
		assertThat(actualSet, equalTo(expectedSet));
	}
}
