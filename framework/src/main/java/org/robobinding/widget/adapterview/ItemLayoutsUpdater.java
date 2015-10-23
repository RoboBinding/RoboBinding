package org.robobinding.widget.adapterview;

import java.util.List;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 */
public class ItemLayoutsUpdater implements RowLayoutsUpdater {
	private final RequiresItemLayoutIds receiver;

	public ItemLayoutsUpdater(RequiresItemLayoutIds receiver) {
		this.receiver = receiver;
	}

	@Override
	public void updateRowLayouts(List<Integer> itemLayoutIds) {
		receiver.setItemLayoutIds(itemLayoutIds);
	}
	
	public static interface RequiresItemLayoutIds {
		void setItemLayoutIds(List<Integer> itemLayoutIds);
	}
}
