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
package robobinding.binding.viewattribute;

import robobinding.beans.Command;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class OnItemClickAttribute extends AbstractCommandViewAttribute
{
	private final AdapterView<?> adapterView;

	public OnItemClickAttribute(AdapterView<?> adapterView, String commandName)
	{
		super(commandName);
		this.adapterView = adapterView;
	}

	@Override
	protected void bind(final Command command)
	{
		adapterView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				ItemClickEvent itemClickEvent = new ItemClickEvent(parent, view, position, id);
				command.invoke(itemClickEvent);
			}
		});
	}

	@Override
	public Class<?>[] getPreferredCommandParameterTypes()
	{
		return new Class<?>[]{ItemClickEvent.class};
	}

}
