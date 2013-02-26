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

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.ViewAttributeValidation;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttribute extends AbstractCommandViewAttribute<CompoundButton> implements ViewListenersAware<CompoundButtonListeners>
{
	private CompoundButtonListeners viewListeners;

	@Override
	public void setViewListeners(CompoundButtonListeners viewListeners)
	{
		this.viewListeners = viewListeners;
	}

	
	@Override
	protected void bind(final Command command)
	{
		viewListeners.addOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				CheckedChangeEvent event = new CheckedChangeEvent(buttonView, isChecked);
				command.invoke(event);
			}
		});
	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return CheckedChangeEvent.class;
	}
	
	@Override
	public void validate(ViewAttributeValidation validation)
	{
		super.validate(validation);
		validation.addErrorIfViewListenersNotSet(viewListeners);
	}
}
