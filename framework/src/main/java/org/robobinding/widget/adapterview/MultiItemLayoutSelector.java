package org.robobinding.widget.adapterview;

import java.util.List;

import org.robobinding.itempresentationmodel.ViewTypeSelectable;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MultiItemLayoutSelector implements ItemLayoutSelector {
	private final List<Integer> itemLayoutIds;
	private final int dropdownLayoutId;
	private final ViewTypeSelectable viewTypeSelector;
	private final int viewTypeCount;
	
	public MultiItemLayoutSelector(List<Integer> itemLayoutIds, int dropdownLayoutId, ViewTypeSelectable viewTypeSelector) {
		this.itemLayoutIds = itemLayoutIds;
		this.dropdownLayoutId = dropdownLayoutId;
		this.viewTypeSelector = viewTypeSelector;
		this.viewTypeCount = itemLayoutIds.size();
	}
	
	@Override
	public int getViewTypeCount() {
		return viewTypeCount;
	}

	@Override
	public int getItemViewType(Object item, int position) {
		ViewTypeSelectionContext<Object> context = new ViewTypeSelectionContext<Object>(getViewTypeCount(), item, position);
		int selectedViewType = userSelectViewType(context);
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
	private int userSelectViewType(ViewTypeSelectionContext<Object> context) {
		return viewTypeSelector.selectViewType(context);
	}
	
	private boolean isInvalidViewType(int viewType) {
		return (viewType < 0) || (viewType >= getViewTypeCount());
	}

	@Override
	public int selectItemLayout(Object item, int position) {
		int index = getItemViewType(item, position);
		return itemLayoutIds.get(index);
	}

	@Override
	public int selectDropdownLayout(Object item, int position) {
		return dropdownLayoutId;
	}
}
