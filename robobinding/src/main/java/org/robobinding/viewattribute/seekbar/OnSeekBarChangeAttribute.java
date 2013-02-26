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
package org.robobinding.viewattribute.seekbar;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.ViewAttributeValidation;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttribute extends AbstractCommandViewAttribute<SeekBar> implements ViewListenersAware<SeekBarListeners>
{
	private SeekBarListeners viewListeners;

	@Override
	public void setViewListeners(SeekBarListeners viewListeners)
	{
		this.viewListeners = viewListeners;
	}
	
	@Override
	protected void bind(final Command command)
	{
		viewListeners.addOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				SeekBarEvent seekBarEvent = new SeekBarEvent(seekBar, progress, fromUser);
				command.invoke(seekBarEvent);
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{
			}
			
		});
	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return SeekBarEvent.class;
	}
	
	@Override
	public void validate(ViewAttributeValidation validation)
	{
		super.validate(validation);
		validation.addErrorIfViewListenersNotSet(viewListeners);
	}
}
