package org.robobinding.widget.adapterview;

import java.util.Collection;

import org.robobinding.PredefinedPendingAttributesForView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DropdownMappingUpdater implements PredefinedMappingUpdater {

	private final DataSetAdapterBuilder dataSetAdapterBuilder;

	public DropdownMappingUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}

	@Override
	public void updateViewMappings(Collection<PredefinedPendingAttributesForView> viewMappings) {
		dataSetAdapterBuilder.setDropdownPredefinedMappings(viewMappings);
	}

}
