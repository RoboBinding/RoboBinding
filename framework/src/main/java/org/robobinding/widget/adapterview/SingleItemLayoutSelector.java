package org.robobinding.widget.adapterview;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SingleItemLayoutSelector implements ItemLayoutSelector {
	private final int itemLayoutId;
	private final int dropdownLayoutId;
	
	public SingleItemLayoutSelector(int itemLayoutId, int dropdownLayoutId) {
		this.itemLayoutId = itemLayoutId;
		this.dropdownLayoutId = dropdownLayoutId;
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
	public int selectItemLayout(Object item, int position) {
		return itemLayoutId;
	}

	@Override
	public int selectDropdownLayout(Object item, int position) {
		return dropdownLayoutId;
	}

}
