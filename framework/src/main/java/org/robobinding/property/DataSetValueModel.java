package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface DataSetValueModel<T> {
	int size();

	T getItem(int position);

	ItemPresentationModel<T> newItemPresentationModel();

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);
}
