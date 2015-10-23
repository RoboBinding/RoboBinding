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
	private final ViewTypeSelectable viewTypeSelector;
	
	public MultiItemLayoutSelector(List<Integer> itemLayoutIds, ViewTypeSelectable viewTypeSelector) {
		this.itemLayoutIds = itemLayoutIds;
		this.viewTypeSelector = viewTypeSelector;
	}
	
	@Override
	public int getViewTypeCount() {
		return itemLayoutIds.size();
	}

	@Override
	public int getItemViewType(Object item, int position) {
		ViewTypeSelectionContext<Object> context = new ViewTypeSelectionContext<Object>(getViewTypeCount(), item, position);
		int selectedViewType = userSelectViewType(context);
		
		checkViewType(selectedViewType);
		
		return selectedViewType;
	}
	
	/**
	 * It is inappropriate to pass in either itemLayoutIds or dropdownLayoutIds, as we don't know view type.
	 */
	private int userSelectViewType(ViewTypeSelectionContext<Object> context) {
		return viewTypeSelector.selectViewType(context);
	}
	
	private void checkViewType(int viewType) {
		if(isInvalidViewType(viewType)) {
			String errorMessage = String.format("invalid selected view type ''%s''. The view type should be in the range [0 ~ %s]", 
					viewType, getViewTypeCount()-1);
			throw new RuntimeException(errorMessage);
		}
	}
	private boolean isInvalidViewType(int viewType) {
		return (viewType < 0) || (viewType >= getViewTypeCount());
	}

	@Override
	public int selectLayout(int viewType) {
		checkViewType(viewType);
		return itemLayoutIds.get(viewType);
	}
}
