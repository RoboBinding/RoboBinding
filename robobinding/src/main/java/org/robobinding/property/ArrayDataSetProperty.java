package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ArrayDataSetProperty<T> extends AbstractDataSetProperty<T> {
    public ArrayDataSetProperty(ObservableBean observableBean, PropertyAccessor<Object> propertyAccessor) {
	super(observableBean, propertyAccessor);
    }

    @Override
    public int size() {
	if (isDataSetNull())
	    return 0;

	T[] array = getDataSet();
	return array.length;
    }

    @Override
    public T getItem(int position) {
	T[] array = getDataSet();
	return array[position];
    }
}
