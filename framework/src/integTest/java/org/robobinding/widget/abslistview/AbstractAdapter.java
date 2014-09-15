package org.robobinding.widget.abslistview;

import android.widget.BaseAdapter;

/**
 * @since 1.0
 * @version 
 * @author Cheng Wei
 *
 */
public abstract class AbstractAdapter extends BaseAdapter {
	  private int itemCount;

	  public AbstractAdapter(int itemCount) {
	    this.itemCount = itemCount;
	  }

	  public void setCount(int itemCount) {
	    this.itemCount = itemCount;
	    notifyDataSetChanged();
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