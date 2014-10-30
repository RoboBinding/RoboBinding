package org.robobinding.viewattribute;

import android.util.SparseArray;

/**
 * View.setTag(int, Object) causes a memory leak if the tag refers to the view
 * itself somehow. This bug is documented here:
 * 
 * http://code.google.com/p/android/issues/detail?id=18273
 */
public class ViewTagger {
	public static final int KEY1 = 1;
	public static final int KEY2 = 2;
	
	private SparseArray<Object> tags = null;

	ViewTagger() {
	}

	public void set(int key, Object value) {
		synchronized (this) {
			if (tags == null)
				tags = new SparseArray<Object>();
			tags.put(key, value);
		}
	}

	public Object get(int key) {
		synchronized (this) {
			if (tags == null)
				return null;
			return tags.get(key);
		}
	}
}