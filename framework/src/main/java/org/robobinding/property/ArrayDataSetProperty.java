package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ArrayDataSetProperty extends AbstractDataSetProperty {
	public ArrayDataSetProperty(ObservableBean observableBean, PropertyAccessor propertyAccessor, ItemPresentationModelFactory factory) {
		super(observableBean, propertyAccessor, factory);
	}

	@Override
	public int size() {
		if (isDataSetNull())
			return 0;

		Object[] array = getDataSet();
		return array.length;
	}

	@Override
	public Object getItem(int position) {
		Object[] array = getDataSet();
		return array[position];
	}
}
