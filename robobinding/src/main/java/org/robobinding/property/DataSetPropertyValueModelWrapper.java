package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DataSetPropertyValueModelWrapper<T> extends PropertyWrapper implements DataSetPropertyValueModel<T> {
    private DataSetPropertyValueModel<T> dataSetPropertyValueModel;

    public DataSetPropertyValueModelWrapper(DataSetPropertyValueModel<T> dataSetPropertyValueModel) {
	super(dataSetPropertyValueModel);
	this.dataSetPropertyValueModel = dataSetPropertyValueModel;
    }

    @Override
    public int size() {
	return dataSetPropertyValueModel.size();
    }

    @Override
    public T getItem(int position) {
	return dataSetPropertyValueModel.getItem(position);
    }

    @Override
    public ItemPresentationModel<T> newItemPresentationModel() {
	return dataSetPropertyValueModel.newItemPresentationModel();
    }

    @Override
    public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	dataSetPropertyValueModel.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	dataSetPropertyValueModel.removePropertyChangeListener(listener);
    }
}
