package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DataSetPropertyValueModelWrapper extends PropertyWrapper implements DataSetPropertyValueModel {
	private final DataSetPropertyValueModel dataSetPropertyValueModel;

	public DataSetPropertyValueModelWrapper(DataSetPropertyValueModel dataSetPropertyValueModel) {
		super(dataSetPropertyValueModel);
		this.dataSetPropertyValueModel = dataSetPropertyValueModel;
	}

	@Override
	public int size() {
		return dataSetPropertyValueModel.size();
	}

	@Override
	public Object getItem(int position) {
		return dataSetPropertyValueModel.getItem(position);
	}

	@Override
	public RefreshableItemPresentationModel newRefreshableItemPresentationModel() {
		return dataSetPropertyValueModel.newRefreshableItemPresentationModel();
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
