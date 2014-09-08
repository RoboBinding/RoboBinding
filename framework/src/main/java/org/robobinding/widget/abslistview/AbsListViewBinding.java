package org.robobinding.widget.abslistview;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

import android.widget.AbsListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class AbsListViewBinding implements ViewBinding<AbsListView> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<AbsListView> mappings) {
	mappings.mapProperty(CheckedItemPositionAttribute.class, "checkedItemPosition");
	mappings.mapMultiTypeProperty(CheckedItemPositionsAttribute.class, "checkedItemPositions");
	mappings.mapProperty(ChoiceModeAttribute.class, "choiceMode");
    }
}
