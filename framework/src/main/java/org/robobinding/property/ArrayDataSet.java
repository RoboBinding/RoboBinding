package org.robobinding.property;

import org.robobinding.itempresentationmodel.DataSetChangeListener;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ArrayDataSet extends AbstractDataSet {
	public ArrayDataSet(RefreshableItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
		super(factory, getSet);
	}

	@Override
	public int size() {
		if (isDataSetNull())
			return 0;

		Object[] array = getDataSet();
		return array.length;
	}

	@Override
	public Object get(int position) {
		Object[] array = getDataSet();
		return array[position];
	}
	
	@Override
	public void addListener(DataSetChangeListener listener) {
		//not supported.
	}

	@Override
	public void removeListener(DataSetChangeListener listener) {
		//not supported.
	}
}
