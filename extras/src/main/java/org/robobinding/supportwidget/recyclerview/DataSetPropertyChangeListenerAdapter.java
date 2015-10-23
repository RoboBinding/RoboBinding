package org.robobinding.supportwidget.recyclerview;

import org.robobinding.itempresentationmodel.DataSetObservable;
import org.robobinding.property.DataSetPropertyChangeListener;

import android.support.v7.widget.RecyclerView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetPropertyChangeListenerAdapter implements DataSetPropertyChangeListener {
	private RecyclerView.Adapter<?> delegate;
	
	public DataSetPropertyChangeListenerAdapter(RecyclerView.Adapter<?> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void propertyChanged() {
		delegate.notifyDataSetChanged();
	}
	
	@Override
	public void onChanged(DataSetObservable sender) {
		delegate.notifyDataSetChanged();
	}

	@Override
	public void onItemChanged(DataSetObservable sender, int position) {
		delegate.notifyItemChanged(position);
	}

	@Override
	public void onItemInserted(DataSetObservable sender, int position) {
		delegate.notifyItemInserted(position);
	}

	@Override
	public void onItemRemoved(DataSetObservable sender, int position) {
		delegate.notifyItemRemoved(position);
	}

	@Override
	public void onItemMoved(DataSetObservable sender, int fromPosition, int toPosition) {
		delegate.notifyItemMoved(fromPosition, toPosition);
	}

	@Override
	public void onItemRangeChanged(DataSetObservable sender, int positionStart, int itemCount) {
		delegate.notifyItemRangeChanged(positionStart, itemCount);
	}

	@Override
	public void onItemRangeInserted(DataSetObservable sender, int positionStart, int itemCount) {
		delegate.notifyItemRangeInserted(positionStart, itemCount);
	}

	@Override
	public void onItemRangeRemoved(DataSetObservable sender, int positionStart, int itemCount) {
		delegate.notifyItemRangeRemoved(positionStart, itemCount);
	}
}
