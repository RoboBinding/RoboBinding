package org.robobinding.property;

import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupedDataSetPropertyValueModelWrapper extends PropertyWrapper implements GroupedDataSetPropertyValueModel {
    private final GroupedDataSetPropertyValueModel dataSetPropertyValueModel;

    public GroupedDataSetPropertyValueModelWrapper(GroupedDataSetPropertyValueModel dataSetPropertyValueModel) {
        super(dataSetPropertyValueModel);
        this.dataSetPropertyValueModel = dataSetPropertyValueModel;
    }

    @Override
    public int size() {
        return dataSetPropertyValueModel.size();
    }

    @Override
    public int sizeOfGroup(int groupPosition) {
        return dataSetPropertyValueModel.sizeOfGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataSetPropertyValueModel.getChild(groupPosition, childPosition);
    }

    @Override
    public RefreshableGroupedItemPresentationModel newRefreshableGroupedItemPresentationModel() {
        return dataSetPropertyValueModel.newRefreshableGroupedItemPresentationModel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        dataSetPropertyValueModel.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        dataSetPropertyValueModel.removePropertyChangeListener(listener);
    }
}