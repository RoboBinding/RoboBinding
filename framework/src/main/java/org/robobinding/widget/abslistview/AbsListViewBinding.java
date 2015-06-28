package org.robobinding.widget.abslistview;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.AbsListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 * @author Cheng Wei
 */
public class AbsListViewBinding implements ViewBinding<AbsListView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<AbsListView> mappings) {
		mappings.mapTwoWayProperty(CheckedItemPositionAttribute.class, "checkedItemPosition");
		mappings.mapTwoWayMultiTypeProperty(CheckedItemPositionsAttribute.class, "checkedItemPositions");

		mappings.mapEvent(OnScrollAttribute.class, "onScroll");
		mappings.mapEvent(OnScrollStateChangedAttribute.class, "onScrollStateChanged");
	}
}
