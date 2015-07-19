package org.robobinding.widgetaddon.abslistview;

import org.robobinding.widgetaddon.adapterview.AdapterViewAddOn;

import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class AbsListViewAddOn extends AdapterViewAddOn {
	private final AbsListView view;
	private final AbsListViewVariant variant;
	
	private OnScrollListeners onScrollListeners;
	
	protected AbsListViewAddOn(AbsListView view, AbsListViewVariant variant) {
		super(view);
		
		this.view = view;
		this.variant = variant;
	}

	public void addOnScrollListener(OnScrollListener onScrollListener) {
		ensureOnScrollListenersInitialized();
		onScrollListeners.addListener(onScrollListener);
	}

	private void ensureOnScrollListenersInitialized() {
		if (onScrollListeners == null) {
			onScrollListeners = new OnScrollListeners();
			view.setOnScrollListener(onScrollListeners);
		}
	}
	
	public void setItemChecked(int position, boolean value){
		variant.setItemChecked(position, value);
	}

	public int getCheckedItemPosition() {
		return variant.getCheckedItemPosition();
	}

	public SparseBooleanArray getCheckedItemPositions() {
		return variant.getCheckedItemPositions();
	}

	public void clearChoices() {
		variant.clearChoices();
	}
}
