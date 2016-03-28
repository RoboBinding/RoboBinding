package org.robobinding.property;

import org.robobinding.itempresentationmodel.DataSetObservable;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyChangeListenerAdapters {
	public static DataSetPropertyChangeListener adapt(final PropertyChangeListener listener) {
		return new DataSetPropertyChangeListener() {
			@Override
			public void propertyChanged() {
				listener.propertyChanged();
			}
			
			@Override
			public void onChanged(DataSetObservable sender) {
				listener.propertyChanged();
			}

			@Override
			public void onItemChanged(DataSetObservable sender, int position) {
				listener.propertyChanged();
			}

			@Override
			public void onItemInserted(DataSetObservable sender, int position) {
				listener.propertyChanged();
			}

			@Override
			public void onItemRemoved(DataSetObservable sender, int position) {
				listener.propertyChanged();
			}

			@Override
			public void onItemMoved(DataSetObservable sender, int fromPosition, int toPosition) {
				listener.propertyChanged();
			}

			@Override
			public void onItemRangeChanged(DataSetObservable sender, int positionStart, int itemCount) {
				listener.propertyChanged();
			}

			@Override
			public void onItemRangeInserted(DataSetObservable sender, int positionStart, int itemCount) {
				listener.propertyChanged();
			}

			@Override
			public void onItemRangeRemoved(DataSetObservable sender, int positionStart, int itemCount) {
				listener.propertyChanged();
			}
		};
	}
	private PropertyChangeListenerAdapters() {}
}
