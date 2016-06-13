package org.robobinding.property;

import java.util.Set;

import org.robobinding.annotation.PreInitializingViews;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.ViewTypeSelectable;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetProperty implements DataSetPropertyValueModel, PropertyChangeListener {
	private final ObservableBean bean;
	private final PropertyDescriptor descriptor;
	private final AbstractDataSet dataSet;
	private final ViewTypeSelectable viewTypeSelector;
	private final PreInitializingViews preInitializingViews;
	
	public DataSetProperty(ObservableBean bean, PropertyDescriptor descriptor, 
			AbstractDataSet dataSet, ViewTypeSelectable viewTypeSelector, 
			PreInitializingViews preInitializingViews) {
		this.bean = bean;
		this.descriptor = descriptor;
		this.dataSet = dataSet;
		this.viewTypeSelector = viewTypeSelector;
		this.preInitializingViews = preInitializingViews;
	}
	
	public DataSetProperty(ObservableBean bean, PropertyDescriptor descriptor, 
			AbstractDataSet dataSet, PreInitializingViews preInitializingViews) {
		this(bean, descriptor, dataSet, ALWAYS_FIRST_VIEW_TYPE, preInitializingViews);
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
	public Object get(int position) {
		return dataSet.get(position);
	}

	@Override
	public RefreshableItemPresentationModel newRefreshableItemPresentationModel(int itemViewType) {
		return dataSet.newRefreshableItemPresentationModel(itemViewType);
	}
	
	@Override
	public int selectViewType(ViewTypeSelectionContext<Object> context) {
		return viewTypeSelector.selectViewType(context);
	}

	@Override
	public void addPropertyChangeListener(DataSetPropertyChangeListener listener) {
		addListener(listener);
		dataSet.addListener(listener);
	}
	
	@Override
	public void removePropertyChangeListener(DataSetPropertyChangeListener listener) {
		removeListener(listener);
		dataSet.removeListener(listener);
	}
	
	public void addListener(PropertyChangeListener listener) {
		bean.addPropertyChangeListener(descriptor.getName(), listener);
	}

	private void removeListener(PropertyChangeListener listener) {
		bean.removePropertyChangeListener(descriptor.getName(), listener);
	}

	@Override
	public void propertyChanged() {
		dataSet.propertyChanged();
	}
	
	@Override
	public boolean preInitializingViewsWithDefault(boolean defaultValue) {
		return preInitializingViews.asBooleanWithDefault(defaultValue);
	}
	
	@Override
	public String toString() {
		return descriptor.getDescription();
	}

	public String decriptionWithDependencies(Set<String> dependentProperties) {
		return descriptor.decriptionWithDependencies(dependentProperties);
	}
	
	private static final ViewTypeSelectable ALWAYS_FIRST_VIEW_TYPE = new ViewTypeSelectable() {
		@Override
		public int selectViewType(ViewTypeSelectionContext<Object> context) {
			return 0;
		}
	};
}
