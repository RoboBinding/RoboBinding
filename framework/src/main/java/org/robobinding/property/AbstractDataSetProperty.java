package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
abstract class AbstractDataSetProperty extends AbstractProperty implements DataSetPropertyValueModel,
	PropertyChangeListener {
    private final ItemPresentationModelFactory factory;
    private boolean isDataSetNotInitialized;
    private Object dataSet;

    protected AbstractDataSetProperty(ObservableBean observableBean, 
	    PropertyAccessor propertyAccessor,
	    ItemPresentationModelFactory factory) {
	super(observableBean, propertyAccessor);

	this.factory = factory;
	isDataSetNotInitialized = true;
    }


    @SuppressWarnings("unchecked")
    protected <DataSetType> DataSetType getDataSet() {
	if (isDataSetNotInitialized) {
	    updateDataSet();
	    isDataSetNotInitialized = false;
	}
	return (DataSetType) dataSet;
    }

    protected void updateDataSet() {
	dataSet = super.getValue();
    }

    protected boolean isDataSetNull() {
	return getDataSet() == null;
    }

    @Override
    public ItemPresentationModel<Object> newItemPresentationModel() {
	return factory.newItemPresentationModel();
    }

    @Override
    public void propertyChanged() {
	updateDataSet();
    }
}
