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

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnFocusChangeAttribute extends AbstractCommandViewAttribute
{
	final View view;
	final FocusChangeType focusChangeType;

	private OnFocusChangeAttribute(View view, String commandName, FocusChangeType focusChangeType)
	{
		super(commandName);
		this.view = view;
		this.focusChangeType = focusChangeType;
	}
	
	@Override
	protected void bind(final Command command)
	{
		ViewListenerUtils.addOnFocusChangeListener(view, new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View view, boolean hasFocus)
			{
				if(focusChangeType.firesNewEvent(hasFocus))
				{
					command.invoke(focusChangeType.createEvent(view, hasFocus));
				}
			}
		});
	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return focusChangeType.getEventType();
	}
	
	public static OnFocusChangeAttribute createOnFocusChange(View view, String commandName)
	{
		return new OnFocusChangeAttribute(view, commandName, FocusChangeType.ON_FOCUS_CHANGE);
	}
	public static OnFocusChangeAttribute createOnFocus(View view, String commandName)
	{
		return new OnFocusChangeAttribute(view, commandName, FocusChangeType.ON_FOCUS);
	}
	public static OnFocusChangeAttribute createOnFocusLost(View view, String commandName)
	{
		return new OnFocusChangeAttribute(view, commandName, FocusChangeType.ON_FOCUS_LOST);
	}
	private enum FocusChangeType
	{
		ON_FOCUS_CHANGE {
			@Override
			public Class<?> getEventType()
			{
				return FocusChangeEvent.class;
			}

			@Override
			public boolean firesNewEvent(boolean hasFocus)
			{
				return true;
			}

			@Override
			public ViewEvent createEvent(View view, boolean hasFocus)
			{
				return new FocusChangeEvent(view, hasFocus);
			}
		},
		ON_FOCUS {
			@Override
			public Class<?> getEventType()
			{
				return ViewEvent.class;
			}

			@Override
			public boolean firesNewEvent(boolean hasFocus)
			{
				return hasFocus;
			}

			@Override
			public ViewEvent createEvent(View view, boolean hasFocus)
			{
				return new ViewEvent(view);
			}
		},
		ON_FOCUS_LOST {
			@Override
			public Class<?> getEventType()
			{
				return ViewEvent.class;
			}

			@Override
			public boolean firesNewEvent(boolean hasFocus)
			{
				return !hasFocus;
			}

			@Override
			public ViewEvent createEvent(View view, boolean hasFocus)
			{
				return new ViewEvent(view);
			}
		};
		public abstract Class<?> getEventType();
		public abstract boolean firesNewEvent(boolean hasFocus);
		public abstract ViewEvent createEvent(View view, boolean hasFocus);
	}
}
