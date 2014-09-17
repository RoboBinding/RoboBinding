package org.robobinding.widget.abslistview;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * @since 1.0
 * @version
 * @author Cheng Wei
 * 
 */
public abstract class AbstractAdapter extends BaseAdapter {
	protected final Context context;
	private final int itemCount;

	public AbstractAdapter(Context context, int itemCount) {
		this.context = context;
		this.itemCount = itemCount;
	}

	@Override
	public int getCount() {
		return itemCount;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}