package org.robobinding.property;

import org.robobinding.itempresentationmodel.ItemViewFactory;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;
import org.robobinding.itempresentationmodel.TypedCursor;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TypedCursorDataSet extends AbstractDataSet {
	public TypedCursorDataSet(RefreshableItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
		super(factory, getSet);
	}

	public TypedCursorDataSet(RefreshableItemPresentationModelFactory factory, ItemViewFactory viewFactory, AbstractGetSet<?> getSet) {
		super(factory, viewFactory, getSet);
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

	@Override
	public void propertyChanged() {
		TypedCursor<Object> oldCursor = getDataSet();
		try {
			updateDataSet();
		} finally {
			if (oldCursor != null) {
				oldCursor.close();
			}
		}
	}
}
