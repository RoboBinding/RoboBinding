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
package org.robobinding.viewattribute.compoundbutton;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttributeValidation;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedAttribute extends AbstractPropertyViewAttribute<CompoundButton, Boolean> implements ViewListenersAware<CompoundButtonListeners>
{
	private CompoundButtonListeners viewListeners;

	@Override
	public void setViewListeners(CompoundButtonListeners viewListeners)
	{
		this.viewListeners = viewListeners;
	}
	
	@Override
	protected void postInitialization()
	{
		ViewAttributeValidation.viewListenersNotNull(viewListeners);
	}

	@Override
	protected void valueModelUpdated(Boolean newValue)
	{
		view.setChecked(newValue);
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<Boolean> valueModel)
	{
		viewListeners.addOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				valueModel.setValue(isChecked);
			}
		});
	}
}
