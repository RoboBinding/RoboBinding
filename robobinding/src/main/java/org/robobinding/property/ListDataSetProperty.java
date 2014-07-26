package org.robobinding.property;

import java.util.List;

import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class ListDataSetProperty extends AbstractDataSetProperty {
    public ListDataSetProperty(ObservableBean observableBean, 
	    PropertyAccessor propertyAccessor,
	    ItemPresentationModelFactory factory) {
	super(observableBean, propertyAccessor, factory);
    }

    @Override
    public int size() {
	if (isDataSetNull())
	    return 0;

	List<Object> list = getDataSet();
	return list.size();
    }

    @Override
    public Object getItem(int index) {
	List<Object> list = getDataSet();
	return list.get(index);
    }
}
