package org.robobinding.widget.adapterview;

import java.util.Collection;

import org.robobinding.PredefinedPendingAttributesForView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemMappingUpdater implements PredefinedMappingUpdater {
    private final DataSetAdapterBuilder dataSetAdapterBuilder;
    
    public ItemMappingUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
	this.dataSetAdapterBuilder = dataSetAdapterBuilder;
    }
    
    @Override
    public void updateViewMappings(Collection<PredefinedPendingAttributesForView> viewMappings) {
	dataSetAdapterBuilder.setItemPredefinedPendingAttributesForViewGroup(viewMappings);
    }

}
