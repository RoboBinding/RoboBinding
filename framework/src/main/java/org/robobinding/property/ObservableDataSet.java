package org.robobinding.property;

import org.robobinding.itempresentationmodel.DataSetChangeListener;
import org.robobinding.itempresentationmodel.DataSetChangeListeners;
import org.robobinding.itempresentationmodel.DataSetObservable;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModelFactory;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ObservableDataSet extends AbstractDataSet {
	private final DispatcherDataSetChangeListener dispatcherListener;
	public ObservableDataSet(RefreshableItemPresentationModelFactory factory, AbstractGetSet<?> getSet) {
		super(factory, getSet);
		
		dispatcherListener = new DispatcherDataSetChangeListener();
		registerDispatcherListener();
	}
	
	private void registerDispatcherListener() {
		DataSetObservable dataSet = getDataSet();
		if(dataSet != null) {
			dataSet.addListener(dispatcherListener);
		}
	}
	
	@Override
	public int size() {
		if (isDataSetNull())
			return 0;

		DataSetObservable dataSet = getDataSet();
		return dataSet.size();
	}

	@Override
	public Object get(int index) {
		DataSetObservable dataSet = getDataSet();
		return dataSet.get(index);
	}
	
	@Override
	public void propertyChanged() {
		DataSetObservable oldDataSet = getDataSet();
		updateDataSet();
		DataSetObservable newDataSet = getDataSet();
		
		if(oldDataSet != newDataSet) {
			if (oldDataSet != null) {
				oldDataSet.removeListener(dispatcherListener);
			}
			if(newDataSet != null) {
				newDataSet.addListener(dispatcherListener);
			}
		}
	}
	
	@Override
	public void addListener(DataSetChangeListener listener) {
		dispatcherListener.addListener(listener);
	}
	
	@Override
	public void removeListener(DataSetChangeListener listener) {
		dispatcherListener.removeListener(listener);
	}
	
	private static class DispatcherDataSetChangeListener implements DataSetChangeListener {
		private DataSetChangeListeners listeners;
		
		public DispatcherDataSetChangeListener() {
			listeners = new DataSetChangeListeners();
		}
		
		@Override
		public void onChanged(DataSetObservable sender) {
			listeners.notifyChanged(sender);
		}

		@Override
		public void onItemChanged(DataSetObservable sender, int position) {
			listeners.notifyItemChanged(sender, position);
		}

		@Override
		public void onItemInserted(DataSetObservable sender, int position) {
			listeners.notifyItemInserted(sender, position);
		}

		@Override
		public void onItemRemoved(DataSetObservable sender, int position) {
			listeners.notifyItemRemoved(sender, position);
		}

		@Override
		public void onItemMoved(DataSetObservable sender, int fromPosition, int toPosition) {
			listeners.notifyItemMoved(sender, fromPosition, toPosition);
		}

		@Override
		public void onItemRangeChanged(DataSetObservable sender, int positionStart, int itemCount) {
			listeners.notifyItemRangeChanged(sender, positionStart, itemCount);
		}

		@Override
		public void onItemRangeInserted(DataSetObservable sender, int positionStart, int itemCount) {
			listeners.notifyItemRangeInserted(sender, positionStart, itemCount);
		}

		@Override
		public void onItemRangeRemoved(DataSetObservable sender, int positionStart, int itemCount) {
			listeners.notifyItemRangeRemoved(sender, positionStart, itemCount);
		}

		public void addListener(DataSetChangeListener listener) {
			listeners.add(listener);
		}

		public void removeListener(DataSetChangeListener listener) {
			listeners.remove(listener);
		}
	}
}