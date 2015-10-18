package org.robobinding.widget.adapterview;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ItemLayoutUpdater implements RowLayoutUpdater {
	private final RequiresItemLayoutId receiver;

	public ItemLayoutUpdater(RequiresItemLayoutId receiver) {
		this.receiver = receiver;
	}

	@Override
	public void updateRowLayout(int itemLayoutId) {
		receiver.setItemLayoutId(itemLayoutId);
	}
	
	public static interface RequiresItemLayoutId {
		void setItemLayoutId(int itemLayoutId);
	}
}
