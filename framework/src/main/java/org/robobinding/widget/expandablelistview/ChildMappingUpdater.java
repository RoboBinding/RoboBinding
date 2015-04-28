package org.robobinding.widget.expandablelistview;

import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.widget.adapterview.PredefinedMappingUpdater;

import java.util.Collection;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
class ChildMappingUpdater implements PredefinedMappingUpdater {
    private final DataSetExpandableListAdapterBuilder adapterBuilder;

	public ChildMappingUpdater(DataSetExpandableListAdapterBuilder adapterBuilder) {
        this.adapterBuilder = adapterBuilder;
	}

	@Override
	public void updateViewMappings(Collection<PredefinedPendingAttributesForView> viewMappings) {
        adapterBuilder.setChildPredefinedPendingAttributesForViewGroup(viewMappings);
    }

}
