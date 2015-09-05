package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface DataSetValueModel {
	int size();

	Object getItem(int position);

	RefreshableItemPresentationModel newRefreshableItemPresentationModel();

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);
}
