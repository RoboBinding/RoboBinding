package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.ViewTypeSelectable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface DataSetValueModel extends ViewTypeSelectable {
	int size();
	Object get(int index);
	void addPropertyChangeListener(DataSetPropertyChangeListener listener);
	void removePropertyChangeListener(DataSetPropertyChangeListener listener);
	RefreshableItemPresentationModel newRefreshableItemPresentationModel(int itemViewType);
	boolean preInitializingViewsWithDefault(boolean defaultValue);
}
