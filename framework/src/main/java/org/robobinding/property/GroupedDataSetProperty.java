package org.robobinding.property;

import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

import java.util.Set;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class GroupedDataSetProperty implements GroupedDataSetPropertyValueModel, PropertyChangeListener {
    private final ObservableBean bean;
    private final PropertyDescriptor descriptor;
    private final AbstractGroupedDataSet dataSet;

    public GroupedDataSetProperty(ObservableBean bean, PropertyDescriptor descriptor, AbstractGroupedDataSet dataSet) {
        this.bean = bean;
        this.descriptor = descriptor;
        this.dataSet = dataSet;
    }

    @Override
    public Class<?> getPropertyType() {
        return descriptor.getPropertyType();
    }

    @Override
    public void checkReadWriteProperty(boolean isReadWriteProperty) {
        descriptor.checkReadWriteProperty(isReadWriteProperty);
    }

    @Override
    public int size() {
        return dataSet.size();
    }

    @Override
    public int sizeOfGroup(int groupPosition) {
        return dataSet.sizeOfGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataSet.getChild(groupPosition, childPosition);
    }

    @Override
    public RefreshableGroupedItemPresentationModel newRefreshableGroupedItemPresentationModel() {
        return dataSet.newRefreshableGroupedItemPresentationModel();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        bean.addPropertyChangeListener(descriptor.getName(), listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        bean.removePropertyChangeListener(descriptor.getName(), listener);
    }

    @Override
    public void propertyChanged() {
        dataSet.propertyChanged();
    }

    @Override
    public String toString() {
        return descriptor.getDescription();
    }

    public String decriptionWithDependencies(Set<String> dependentProperties) {
        return descriptor.decriptionWithDependencies(dependentProperties);
    }
}
