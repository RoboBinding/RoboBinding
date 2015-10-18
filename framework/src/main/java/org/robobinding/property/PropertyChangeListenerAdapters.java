package org.robobinding.property;

import org.robobinding.itempresentationmodel.DataSetChangeListener;
import org.robobinding.itempresentationmodel.DataSetObservable;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyChangeListenerAdapters {
	public static DataSetChangeListener adapt(final PropertyChangeListener listener) {
		return new DataSetChangeListener() {
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
	
	private static DataSetObservable KNOWN_SENDER = new DataSetObservable(){
		@Override
		public void addListener(DataSetChangeListener listener) {};
		@Override
		public void removeListener(DataSetChangeListener listener) {};
		@Override
		public Object get(int index) {
			throw new UnsupportedOperationException();
		}
		@Override
		public int size() {
			throw new UnsupportedOperationException();
		}
	};
	
	public static PropertyChangeListener adapt(final DataSetChangeListener listener) {
		return new PropertyChangeListener() {
			
			@Override
			public void propertyChanged() {
				listener.onChanged(KNOWN_SENDER);
			}
		};
	}
	
	private PropertyChangeListenerAdapters() {}
}
