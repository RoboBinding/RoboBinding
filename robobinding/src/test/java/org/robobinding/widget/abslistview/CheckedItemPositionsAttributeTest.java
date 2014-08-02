package org.robobinding.widget.abslistview;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.AbstractCheckedItemPositionsAttribute;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.MapCheckedItemPositionsAttribute;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SetCheckedItemPositionsAttribute;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;
import org.robobinding.widget.adapterview.AdapterViewListeners;

import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionsAttributeTest extends AbstractMultiTypePropertyViewAttributeTest<CheckedItemPositionsAttribute> {
    @Override
    protected void setTypeMappingExpectations() {
	forPropertyType(SparseBooleanArray.class).expectAttribute(SparseBooleanArrayCheckedItemPositionsAttribute.class);
	forPropertyType(Set.class).expectAttribute(SetCheckedItemPositionsAttribute.class);
	forPropertyType(Map.class).expectAttribute(MapCheckedItemPositionsAttribute.class);
    }

    @Test
    public void whenBinding_thenRegisterWithMulticastListener() {
	CheckedItemPositionsAttributeForTest attribute =
		new CheckedItemPositionsAttributeForTest();
	AdapterViewListeners mockAdapterViewListeners = mock(AdapterViewListeners.class);
	attribute.setViewListeners(mockAdapterViewListeners);

	attribute.observeChangesOnTheView(null, null);

	verify(mockAdapterViewListeners).addOnItemClickListener(any(OnItemClickListener.class));
    }

    class CheckedItemPositionsAttributeForTest extends AbstractCheckedItemPositionsAttribute<Integer> {
	@Override
	public void updateView(AbsListView view, Integer newValue) {
	}

	@Override
	protected void viewCheckedItemPositionsChanged(AbsListView view, ValueModel<Integer> valueModel) {
	}
    }
}