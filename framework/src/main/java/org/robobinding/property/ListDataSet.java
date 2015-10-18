package org.robobinding.property;

import java.util.List;

import org.robobinding.itempresentationmodel.DataSetChangeListener;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ListDataSet extends AbstractDataSet {
	public ListDataSet(RefreshableItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
		super(factory, getSet);
	}
	
	@Override
	public int size() {
		if (isDataSetNull())
			return 0;

		List<Object> list = getDataSet();
		return list.size();
	}

	@Override
	public Object get(int index) {
		List<Object> list = getDataSet();
		return list.get(index);
	}
	
	@Override
	public void addListener(DataSetChangeListener listener) {
		//not supported
	}
	
	@Override
	public void removeListener(DataSetChangeListener listener) {
		//not supported.
	}
}
