package org.robobinding.property;

import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public interface GroupedDataSetValueModel<T> {
    int size();

    int sizeOfGroup(int groupPosition);

    T getChild(int groupPosition, int childPosition);

    RefreshableGroupedItemPresentationModel newRefreshableGroupedItemPresentationModel();

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
