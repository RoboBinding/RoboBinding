package org.robobinding.widget.abslistview;

import android.widget.AbsListView;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class AbsListViewAttributeMapper implements BindingAttributeMapper<AbsListView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<AbsListView> mappings) {
		mappings.mapProperty(CheckedItemPositionAttribute.class, "checkedItemPosition");
		mappings.mapMultiTypeProperty(CheckedItemPositionsAttribute.class, "checkedItemPositions");
		mappings.mapProperty(ChoiceModeAttribute.class, "choiceMode");
	}
}
