package org.robobinding.property;

import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

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
	public Object get(int position) {
		return valueModel.get(position);
	}

	@Override
	public RefreshableItemPresentationModel newRefreshableItemPresentationModel(int itemViewType) {
		return valueModel.newRefreshableItemPresentationModel(itemViewType);
	}
	
	@Override
	public int selectViewType(ViewTypeSelectionContext<Object> context) {
		return valueModel.selectViewType(context);
	}

	@Override
	public void addPropertyChangeListener(DataSetPropertyChangeListener listener) {
		valueModel.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(DataSetPropertyChangeListener listener) {
		valueModel.removePropertyChangeListener(listener);
	}

	@Override
	public boolean preInitializingViewsWithDefault(boolean defaultValue) {
		return valueModel.preInitializingViewsWithDefault(defaultValue);
	}
}
