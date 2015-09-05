package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetValueModelWrapper implements DataSetValueModel {
	private final DataSetValueModel valueModel;

	public DataSetValueModelWrapper(DataSetValueModel valueModel) {
		this.valueModel = valueModel;
	}

	@Override
	public int size() {
		return valueModel.size();
	}

	@Override
	public Object getItem(int position) {
		return valueModel.getItem(position);
	}

	@Override
	public RefreshableItemPresentationModel newRefreshableItemPresentationModel() {
		return valueModel.newRefreshableItemPresentationModel();
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
