package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SetCheckedItemPositionsAttribute;
import org.robolectric.annotation.Config;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class SetCheckedItemPositionsAttributeTest extends AbstractCheckedItemPositionsAttributeTest {
	private Set<Integer> checkedItemPositions;

	@Before
	public void setUpTestData() {
		checkedItemPositions = SparseBooleanArrayUtils.toSet(anySparseBooleanArray());
	}

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		SetCheckedItemPositionsAttribute attribute = new SetCheckedItemPositionsAttribute();
		attribute.updateView(view, checkedItemPositions);

		assertThat(SparseBooleanArrayUtils.toSet(view.getCheckedItemPositions()), equalTo(checkedItemPositions));
	}

	@Test
	public void whenObserveChangesOnTheView_thenValueModelShouldReceiveTheChange() {
		SetCheckedItemPositionsAttribute attribute = withListenersSet(new SetCheckedItemPositionsAttribute());
		Set<Integer> emptySet = Sets.newHashSet();
		ValueModel<Set<Integer>> valueModel = ValueModelUtils.create(emptySet);
		attribute.observeChangesOnTheView(view, valueModel);

		setItemsChecked(checkedItemPositions);

		assertThat(valueModel.getValue(), equalTo(checkedItemPositions));
	}

}