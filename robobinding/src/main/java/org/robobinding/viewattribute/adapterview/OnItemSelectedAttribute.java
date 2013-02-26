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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.ViewAttributeValidation;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.database.DataSetObserver;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemSelectedAttribute extends AbstractCommandViewAttribute<AdapterView<?>> implements ViewListenersAware<AdapterViewListeners>
{
	private AdapterViewListeners adapterViewListeners;

	@Override
	protected void bind(final Command command)
	{
		adapterViewListeners.addOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
				command.invoke(itemClickEvent);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				ItemClickEvent itemClickEvent = new ItemClickEvent(parent, null, AdapterView.INVALID_POSITION, 0);
				command.invoke(itemClickEvent);
			}
		});
		
		view.getAdapter().registerDataSetObserver(new DataSetObserver() {
			public void onChanged() {
				int position = view.getSelectedItemPosition();
				
				if (position >= view.getCount())
					return;
				
				ItemClickEvent itemClickEvent = new ItemClickEvent(view, view.getChildAt(position), position, 0);
				command.invoke(itemClickEvent);
		    }
		});
	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return ItemClickEvent.class;
	}

	@Override
	public void setViewListeners(AdapterViewListeners adapterViewListeners)
	{
		this.adapterViewListeners = adapterViewListeners;
	}
	
	@Override
	public void validate(ViewAttributeValidation validation)
	{
		super.validate(validation);
		validation.addErrorIfViewListenersNotSet(adapterViewListeners);
	}
}
