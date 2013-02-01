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
package org.robobinding.viewattribute.view;

import org.robobinding.attribute.Command;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.CommandViewAttributeConfig;
import org.robobinding.viewattribute.ViewAttributeValidation;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractFocusChangeAttribute extends AbstractCommandViewAttribute<View> implements ViewListenersAware<ViewListeners>
{
	private ViewListeners viewListeners;

	public AbstractFocusChangeAttribute(CommandViewAttributeConfig<View> config)
	{
		super(config);
	}
	
	@Override
	public void setViewListeners(ViewListeners viewListeners)
	{
		this.viewListeners = viewListeners;
	}
	
	@Override
	protected void postConstruct()
	{
		ViewAttributeValidation.viewListenersNotNull(viewListeners);
	}
	
	@Override
	protected void bind(final Command command)
	{
		viewListeners.addOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View view, boolean hasFocus)
			{
				if (firesNewEvent(hasFocus))
				{
					command.invoke(createEvent(view, hasFocus));
				}
			}
		});

	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return getEventType();
	}

	protected abstract Class<?> getEventType();
	protected abstract boolean firesNewEvent(boolean hasFocus);
	protected abstract AbstractViewEvent createEvent(View view, boolean hasFocus);
}
