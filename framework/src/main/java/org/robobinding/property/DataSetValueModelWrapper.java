package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetValueModelWrapper<T> implements DataSetValueModel<T> {
	private final DataSetValueModel<T> valueModel;

	public DataSetValueModelWrapper(DataSetValueModel<T> valueModel) {
		this.valueModel = valueModel;
	}

	@Override
	public int size() {
		return valueModel.size();
	}

	@Override
	public T getItem(int position) {
		return valueModel.getItem(position);
	}

	@Override
	public RefreshableItemPresentationModel newRefreshableItemPresentationModel(T item) {
		return valueModel.newRefreshableItemPresentationModel(item);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		valueModel.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		valueModel.removePropertyChangeListener(listener);
	}


	@Override
	public int getItemViewTypeCount() {
		return valueModel.getItemViewTypeCount();
	}

	@Override
	public int getItemViewType(int position, Object item) {
		return valueModel.getItemViewType(position, item);
	}

	@Override
	public int getItemLayoutId(int position, Object item) {
		return valueModel.getItemLayoutId(position, item);
	}
}
