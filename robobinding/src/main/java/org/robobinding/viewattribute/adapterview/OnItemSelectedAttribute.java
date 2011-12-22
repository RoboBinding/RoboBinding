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

import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.Command;
import org.robobinding.viewattribute.view.ViewListenerUtils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnItemSelectedAttribute extends AbstractCommandViewAttribute
{
	private final AdapterView<?> adapterView;

	public OnItemSelectedAttribute(AdapterView<?> adapterView, String commandName)
	{
		super(commandName);
		this.adapterView = adapterView;
	}

	@Override
	protected void bind(final Command command)
	{
		ViewListenerUtils.addOnItemSelectedListener(adapterView, new OnItemSelectedListener() {
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
	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return ItemClickEvent.class;
	}

}
