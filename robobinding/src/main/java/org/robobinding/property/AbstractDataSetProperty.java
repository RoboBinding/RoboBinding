package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

import com.google.common.base.Strings;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
abstract class AbstractDataSetProperty<T> extends AbstractProperty<Object> implements DataSetPropertyValueModel<T>,
	PresentationModelPropertyChangeListener {
    ItemPresentationModelFactory<T> factory;
    private boolean isDataSetNotInitialized;
    private Object dataSet;

    protected AbstractDataSetProperty(ObservableBean observableBean, PropertyAccessor<Object> propertyAccessor) {
	super(observableBean, propertyAccessor);

	initializeFactory();

	isDataSetNotInitialized = true;
	addPropertyChangeListener(this);
    }

    private void initializeFactory() {
	org.robobinding.presentationmodel.ItemPresentationModel annotation = getPropertyAccessor().getAnnotation(
		org.robobinding.presentationmodel.ItemPresentationModel.class);
	@SuppressWarnings("unchecked")
	Class<? extends ItemPresentationModel<T>> itemPresentationModelClass = (Class<? extends ItemPresentationModel<T>>) annotation.value();
	String factoryMethod = annotation.factoryMethod();
	if (Strings.isNullOrEmpty(factoryMethod)) {
	    factory = new DefaultConstructorImpl<T>(itemPresentationModelClass);
	} else {
	    factory = new FactoryMethodImpl<T>(getBean(), itemPresentationModelClass, factoryMethod);
	}
    }

    @SuppressWarnings("unchecked")
    protected <DataSetType> DataSetType getDataSet() {
	if (isDataSetNotInitialized) {
	    updateDataSet();
	    isDataSetNotInitialized = false;
	}
	return (DataSetType) dataSet;
    }

    private void updateDataSet() {
	dataSet = super.getValue();
    }

    protected boolean isDataSetNull() {
	return getDataSet() == null;
    }

    @Override
    public ItemPresentationModel<T> newItemPresentationModel() {
	return factory.newItemPresentationModel();
    }

    @Override
    public final void propertyChanged() {
	updateDataSet();
    }
}
