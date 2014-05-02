package org.robobinding.property;

import java.util.List;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class ListDataSetProperty<T> extends AbstractDataSetProperty<T> {
    public ListDataSetProperty(ObservableBean observableBean, PropertyAccessor<Object> propertyAccessor) {
	super(observableBean, propertyAccessor);
    }

    @Override
    public int size() {
	if (isDataSetNull())
	    return 0;

	List<T> list = getDataSet();
	return list.size();
    }

    @Override
    public T getItem(int index) {
	List<T> list = getDataSet();
	return list.get(index);
    }
}
