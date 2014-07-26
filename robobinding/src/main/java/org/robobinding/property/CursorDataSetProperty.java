package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;
import org.robobinding.itempresentationmodel.TypedCursor;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
class CursorDataSetProperty extends AbstractDataSetProperty {
    public CursorDataSetProperty(ObservableBean observableBean, 
	    PropertyAccessor propertyAccessor,
	    ItemPresentationModelFactory factory) {
	super(observableBean, propertyAccessor, factory);
    }

    @Override
    public int size() {
	if (isDataSetNull()) {
	    return 0;
	}
	TypedCursor<Object> cursor = getDataSet();
	return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
	TypedCursor<Object> cursor = getDataSet();
	return cursor.getObjectAtPosition(position);
    }
}
