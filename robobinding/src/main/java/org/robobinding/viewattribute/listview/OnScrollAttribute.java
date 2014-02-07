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

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * 
 * @author jihunlee
 * 
 */
public class OnScrollAttribute extends AbstractCommandViewAttribute<ListView>
		implements
			ViewListenersAware<ListViewListeners> {
	private ListViewListeners listViewListeners;

	@Override
	public void setViewListeners(ListViewListeners listViewListeners) {
		this.listViewListeners = listViewListeners;

	}

	@Override
	protected void bind(final Command command) {
		listViewListeners.addOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				ScrollEvent scrollEvent = new ScrollEvent(view,
						firstVisibleItem, visibleItemCount, totalItemCount);
				command.invoke(scrollEvent);
			}
		});

	}
	@Override
	protected Class<?> getPreferredCommandParameterType() {
		return ScrollEvent.class;
	}

}
