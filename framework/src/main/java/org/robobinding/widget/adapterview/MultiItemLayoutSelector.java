package org.robobinding.widget.adapterview;

import java.util.List;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MultiItemLayoutSelector implements ItemLayoutSelector {
	private final List<Integer> itemLayoutIds;
	private final List<Integer> dropdownLayoutIds;
	private final int viewTypeCount;
	
	public MultiItemLayoutSelector(List<Integer> itemLayoutIds, List<Integer> dropdownLayoutIds) {
		this.itemLayoutIds = itemLayoutIds;
		this.dropdownLayoutIds = dropdownLayoutIds;
		this.viewTypeCount = Math.max(itemLayoutIds.size(), dropdownLayoutIds.size());
	}
	
	@Override
	public int getViewTypeCount() {
		return viewTypeCount;
	}

	@Override
	public int getItemViewType(Object item, int position) {
		int selectedViewType = userSelectViewType(getViewTypeCount(), item, position);
		if(isInvalidViewType(selectedViewType)) {
			String errorMessage = String.format("invalid selected view type ''%s''. The view type should be in the range [0 ~ %s]", 
					selectedViewType, getViewTypeCount()-1);
			throw new RuntimeException(errorMessage);
		}
		
		return selectedViewType;
	}
	
	/**
	 * It is inappropriate to pass in either itemLayoutIds or dropdownLayoutIds, as we don't know view type.
	 */
	private int userSelectViewType(int viewTypeCount, Object item, int position) {
		return 0;
	}
	
	private boolean isInvalidViewType(int viewType) {
		return (viewType < 0) || (viewType >= getViewTypeCount());
	}

	@Override
	public int selectItemLayout(Object item, int position) {
		return selectLayout(itemLayoutIds, item, position);
	}
	
	private int selectLayout(List<Integer> layoutIds, Object item, int position) {
		int index = getItemViewType(item, position);
		int adjustedIndex = adjustIndex(index, layoutIds.size()-1);
		return layoutIds.get(adjustedIndex);
	}
	
	private int adjustIndex(int index, int maxIndex) {
		int defaultIndex = 0;
		return index > maxIndex ? defaultIndex : index;
	}

	@Override
	public int selectDropdownLayout(Object item, int position) {
		return selectLayout(dropdownLayoutIds, item, position);
	}
}
