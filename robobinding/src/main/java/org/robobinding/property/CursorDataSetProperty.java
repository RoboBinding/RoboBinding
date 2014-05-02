package org.robobinding.property;

import org.robobinding.itempresentationmodel.TypedCursor;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
class CursorDataSetProperty<T> extends AbstractDataSetProperty<T> {
    public CursorDataSetProperty(ObservableBean observableBean, PropertyAccessor<Object> propertyAccessor) {
	super(observableBean, propertyAccessor);
    }

    @Override
    public int size() {
	if (isDataSetNull()) {
	    return 0;
	}
	TypedCursor<T> cursor = getDataSet();
	return cursor.getCount();
    }

    @Override
    public T getItem(int position) {
	TypedCursor<T> cursor = getDataSet();
	return cursor.getObjectAtPosition(position);
    }
}
