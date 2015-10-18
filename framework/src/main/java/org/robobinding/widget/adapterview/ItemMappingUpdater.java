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
	private final RequiresItemPredefinedMappings receiver;

	public ItemMappingUpdater(RequiresItemPredefinedMappings receiver) {
		this.receiver = receiver;
	}

	@Override
	public void updateViewMappings(Collection<PredefinedPendingAttributesForView> viewMappings) {
		receiver.setItemPredefinedMappings(viewMappings);
	}

	public static interface RequiresItemPredefinedMappings {
		void setItemPredefinedMappings(Collection<PredefinedPendingAttributesForView> itemPredefinedMappings);
	}
}
