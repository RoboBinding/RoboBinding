package org.robobinding.itempresentationmodel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface DataSetChangeListener {
	void onChanged(DataSetObservable sender);
	
	void onItemChanged(DataSetObservable sender, int position);
	void onItemInserted(DataSetObservable sender, int position);
	void onItemRemoved(DataSetObservable sender, int position);
	void onItemMoved(DataSetObservable sender, int fromPosition, int toPosition);
	
	void onItemRangeChanged(DataSetObservable sender, int positionStart, int itemCount);
	void onItemRangeInserted(DataSetObservable sender, int positionStart, int itemCount);
	void onItemRangeRemoved(DataSetObservable sender, int positionStart, int itemCount);
}
