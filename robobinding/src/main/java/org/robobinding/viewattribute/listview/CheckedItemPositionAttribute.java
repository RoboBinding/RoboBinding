/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.adapterview.AdapterViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CheckedItemPositionAttribute extends AbstractPropertyViewAttribute<ListView, Integer> implements ViewListenersAware<AdapterViewListeners>
{
	private AdapterViewListeners adapterViewListeners;

	@Override
	public void setViewListeners(AdapterViewListeners adapterViewListeners)
	{
		this.adapterViewListeners = adapterViewListeners;
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<Integer> valueModel)
	{
		adapterViewListeners.addOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View itemView, int position, long id)
			{
				int checkedItemPosition = view.getCheckedItemPosition();
				valueModel.setValue(checkedItemPosition);
			}
		});
	}
	
	@Override
	protected void valueModelUpdated(Integer newValue)
	{
		view.setItemChecked(newValue, true);
	}
}
