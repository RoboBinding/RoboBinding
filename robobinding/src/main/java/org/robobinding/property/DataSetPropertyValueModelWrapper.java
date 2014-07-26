package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModel;

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
    public ItemPresentationModel<Object> newItemPresentationModel() {
	return dataSetPropertyValueModel.newItemPresentationModel();
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
