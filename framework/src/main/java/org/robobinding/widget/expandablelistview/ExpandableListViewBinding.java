package org.robobinding.widget.expandablelistview;

import static org.robobinding.widget.expandablelistview.AdaptedExpandableListViewDataSetAttributes.GROUP_SOURCE;
import static org.robobinding.widget.expandablelistview.AdaptedExpandableListViewDataSetAttributes.GROUP_LAYOUT;
import static org.robobinding.widget.expandablelistview.AdaptedExpandableListViewDataSetAttributes.GROUP_MAPPING;
import static org.robobinding.widget.expandablelistview.AdaptedExpandableListViewDataSetAttributes.CHILD_SOURCE;
import static org.robobinding.widget.expandablelistview.AdaptedExpandableListViewDataSetAttributes.CHILD_LAYOUT;
import static org.robobinding.widget.expandablelistview.AdaptedExpandableListViewDataSetAttributes.CHILD_MAPPING;

import android.widget.ExpandableListView;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.*;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ExpandableListViewBinding implements ViewBinding<ExpandableListView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ExpandableListView> mappings) {
        mappings.mapGroupedAttribute(AdaptedExpandableListViewDataSetAttributes.class,
                SOURCE, ITEM_LAYOUT, ITEM_MAPPING,
                GROUP_SOURCE, GROUP_LAYOUT, GROUP_MAPPING,
                CHILD_SOURCE, CHILD_LAYOUT, CHILD_MAPPING);

        mappings.mapEvent(OnGroupClickAttribute.class, "onGroupClick");
        mappings.mapEvent(OnChildClickAttribute.class, "onChildClick");
        mappings.mapEvent(OnGroupExpandAttribute.class, "onGroupExpand");
        mappings.mapEvent(OnGroupCollapseAttribute.class, "onGroupCollapse");

	}
}
