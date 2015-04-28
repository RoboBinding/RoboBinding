package org.robobinding.widget.expandablelistview;

import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.widget.adapterview.PredefinedMappingUpdater;

import java.util.Collection;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupMappingUpdater implements PredefinedMappingUpdater {
	private final DataSetExpandableListAdapterBuilder adapterBuilder;

	public GroupMappingUpdater(DataSetExpandableListAdapterBuilder adapterBuilder) {
		this.adapterBuilder = adapterBuilder;
	}

	@Override
	public void updateViewMappings(Collection<PredefinedPendingAttributesForView> viewMappings) {
        adapterBuilder.setGroupPredefinedPendingAttributesForViewGroup(viewMappings);
	}

}
