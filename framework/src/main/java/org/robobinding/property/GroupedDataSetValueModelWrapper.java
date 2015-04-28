package org.robobinding.property;

import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupedDataSetValueModelWrapper<T> implements GroupedDataSetValueModel<T> {
    private final GroupedDataSetValueModel<T> valueModel;

    public GroupedDataSetValueModelWrapper(GroupedDataSetValueModel<T> valueModel) {
        this.valueModel = valueModel;
    }

    @Override
    public int size() {
        return valueModel.size();
    }

    @Override
    public int sizeOfGroup(int groupPosition) {
        return valueModel.sizeOfGroup(groupPosition);
    }

    @Override
    public T getChild(int groupPosition, int childPosition) {
        return valueModel.getChild(groupPosition, childPosition);
    }

    @Override
    public RefreshableGroupedItemPresentationModel newRefreshableGroupedItemPresentationModel() {
        return valueModel.newRefreshableGroupedItemPresentationModel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        valueModel.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        valueModel.removePropertyChangeListener(listener);
    }
}
