package org.robobinding.widget.abslistview;

import java.util.Map;
import java.util.Set;

import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.MapCheckedItemPositionsAttribute;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SetCheckedItemPositionsAttribute;
import org.robobinding.widget.abslistview.CheckedItemPositionsAttribute.SparseBooleanArrayCheckedItemPositionsAttribute;
import org.robolectric.annotation.Config;

import android.util.SparseBooleanArray;
import android.widget.AbsListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
public class CheckedItemPositionsAttributeTest extends AbstractMultiTypePropertyViewAttributeTest {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(SparseBooleanArray.class).expectAttributeType(SparseBooleanArrayCheckedItemPositionsAttribute.class);
		forPropertyType(Set.class).expectAttributeType(SetCheckedItemPositionsAttribute.class);
		forPropertyType(Map.class).expectAttributeType(MapCheckedItemPositionsAttribute.class);
	}
	
	@Override
	protected TwoWayPropertyViewAttribute<AbsListView, ?, ?> createViewAttribute(Class<?> propertyType) {
		return new CheckedItemPositionsAttribute().create(null, propertyType);
	}
}