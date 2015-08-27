package org.robobinding.property;

import java.util.Set;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetProperty implements DataSetPropertyValueModel, PropertyChangeListener {
	private final ObservableBean bean;
	private final PropertyDescriptor descriptor;
	private final AbstractDataSet dataSet;
	
	public DataSetProperty(ObservableBean bean, PropertyDescriptor descriptor, AbstractDataSet dataSet) {
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
	public Object getItem(int position) {
		return dataSet.getItem(position);
	}

	@Override
	public RefreshableItemPresentationModel newRefreshableItemPresentationModel(Object item) {
		return dataSet.newRefreshableItemPresentationModel(item);
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

	@Override
	public int getItemViewTypeCount() {
		return dataSet.getItemViewTypeCount();
	}

	@Override
	public int getItemViewType(int position, Object item) {
		return dataSet.getItemViewType(position, item);
	}

	@Override
	public int getItemLayoutId(int position, Object item) {
		return dataSet.getItemLayoutId(position, item);
	}
}
