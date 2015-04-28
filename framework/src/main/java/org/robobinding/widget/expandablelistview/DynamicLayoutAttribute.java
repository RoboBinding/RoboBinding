package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;
import org.robobinding.viewattribute.property.AlwaysPreInitializingView;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
class DynamicLayoutAttribute implements PropertyViewAttribute<ExpandableListView, Integer>, AlwaysPreInitializingView {
	private final ExpandableRowLayoutUpdater expandableRowLayoutUpdater;
	private final DataSetExpandableListAdapterUpdater adapterUpdater;

	public DynamicLayoutAttribute(ExpandableRowLayoutUpdater expandableRowLayoutUpdater, DataSetExpandableListAdapterUpdater adapterUpdater) {
		this.expandableRowLayoutUpdater = expandableRowLayoutUpdater;
		this.adapterUpdater = adapterUpdater;
	}

	@Override
	public void updateView(ExpandableListView view, Integer newItemLayoutId) {
        expandableRowLayoutUpdater.updateRowLayout(newItemLayoutId);
        adapterUpdater.update();
	}
}