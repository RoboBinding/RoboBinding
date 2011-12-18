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
package org.robobinding.binding.viewattribute;

import org.robobinding.property.PropertyValueModel;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CheckedAttribute extends AbstractPropertyViewAttribute<Boolean>
{
	private final CompoundButton compoundButton;
	private boolean updatedProgrammatically;

	public CheckedAttribute(CompoundButton compoundButton, String attributeValue, boolean preInitializeView)
	{
		super(attributeValue, preInitializeView);
		this.compoundButton = compoundButton;
	}

	@Override
	protected void valueModelUpdated(Boolean newValue)
	{
		updatedProgrammatically = true;
		compoundButton.setChecked(newValue);
		updatedProgrammatically = false;
	}

	@Override
	protected void observeChangesOnTheView(final PropertyValueModel<Boolean> valueModel)
	{
		compoundButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(!updatedProgrammatically)
				{
					valueModel.setValue(isChecked);
				}
			}
		});
	}

}
