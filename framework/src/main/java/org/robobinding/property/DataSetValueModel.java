package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemViewFactory;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface DataSetValueModel<T> extends ItemViewFactory {
	int size();

	T getItem(int position);

	RefreshableItemPresentationModel newRefreshableItemPresentationModel(T bean);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);
}
