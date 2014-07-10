/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.AbstractListeners;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 
 * @author jihunlee
 *
 */
public class OnScrollListeners extends AbstractListeners<OnScrollListener>
		implements
			OnScrollListener {

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		for (OnScrollListener listener : listeners) {
			listener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		for (OnScrollListener listener : listeners) {
			listener.onScrollStateChanged(view, scrollState);
		}

	}

	public static OnScrollListeners convert(OnScrollListener listener) {
		if (listener instanceof OnScrollListeners) {
			return (OnScrollListeners) listener;
		} else {
			OnScrollListeners onScrollListeners = new OnScrollListeners();
			onScrollListeners.addListener(listener);
			return onScrollListeners;
		}
	}

}
