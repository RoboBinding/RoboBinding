package org.robobinding.widget.abslistview;

import org.robobinding.widget.adapterview.MockAdapterViewAddOn;
import org.robobinding.widgetaddon.abslistview.AbsListViewAddOn;

import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockAbsListViewAddOn extends AbsListViewAddOn {
	private AbsListView view;
	private MockAdapterViewAddOn adapterViewAddOn;
	
	public boolean addOnItemSelectedListenerInvoked;
	public boolean addOnItemClickListenerInvoked;
	public boolean addOnScrollListenerInvoked = false;

	public MockAbsListViewAddOn(AbsListView view) {
		super(null, null);
		this.view = view;
		this.adapterViewAddOn = new MockAdapterViewAddOn(view);
	}

	@Override
	public void addOnScrollListener(OnScrollListener onScrollListener) {
		addOnScrollListenerInvoked = true;
		view.setOnScrollListener(onScrollListener);
	}
	
	@Override
	public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
		adapterViewAddOn.addOnItemClickListener(onItemClickListener);
		this.addOnItemClickListenerInvoked = adapterViewAddOn.addOnItemClickListenerInvoked;
	}
	
	@Override
	public int getCheckedItemPosition() {
		return view.getCheckedItemPosition();
	}
	
	@Override
	public void setItemChecked(int position, boolean value) {
		view.setItemChecked(position, value);
	}
	
	@Override
	public SparseBooleanArray getCheckedItemPositions() {
		return view.getCheckedItemPositions();
	}
	
	@Override
	public void clearChoices() {
		view.clearChoices();
	}
}
