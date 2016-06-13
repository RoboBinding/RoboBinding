package org.robobinding.itempresentationmodel;

import java.util.Set;

import org.robobinding.util.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetChangeListeners {
	private final Set<DataSetChangeListener> listeners;

	public DataSetChangeListeners() {
		this.listeners = Sets.newLinkedHashSet();
	}

	public void add(DataSetChangeListener listener) {
		listeners.add(listener);
	}

	public boolean remove(DataSetChangeListener listener) {
		return listeners.remove(listener);
	}

	public boolean contains(DataSetChangeListener listener) {
		return listeners.contains(listener);
	}

	public void notifyChanged(DataSetObservable sender) {
		for (DataSetChangeListener listener : listeners) {
			listener.onChanged(sender);
		}
	}
	
	public void notifyItemChanged(DataSetObservable sender, int position) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemChanged(sender, position);
		}
	}
	
	public void notifyItemInserted(DataSetObservable sender, int position) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemInserted(sender, position);
		}
	}
	
	public void notifyItemRemoved(DataSetObservable sender, int position) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemRemoved(sender, position);
		}
	}
	
	public void notifyItemMoved(DataSetObservable sender, int fromPosition, int toPosition) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemMoved(sender, fromPosition, toPosition);
		}
	}
	
	public void notifyItemRangeChanged(DataSetObservable sender, int positionStart, int itemCount) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemRangeChanged(sender, positionStart, itemCount);
		}
	}
	
	public void notifyItemRangeInserted(DataSetObservable sender, int positionStart, int itemCount) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemRangeInserted(sender, positionStart, itemCount);
		}
	}
	
	public void notifyItemRangeRemoved(DataSetObservable sender, int positionStart, int itemCount) {
		for (DataSetChangeListener listener : listeners) {
			listener.onItemRangeRemoved(sender, positionStart, itemCount);
		}
	}
}
