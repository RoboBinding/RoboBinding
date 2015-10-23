package org.robobinding.widget.adapterview;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SingleItemLayoutSelector implements ItemLayoutSelector {
	private final int itemLayoutId;
	
	public SingleItemLayoutSelector(int itemLayoutId) {
		this.itemLayoutId = itemLayoutId;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public int getItemViewType(Object item, int position) {
		return 0;
	}

	@Override
	public int selectLayout(int viewType) {
		return itemLayoutId;
	}
}
