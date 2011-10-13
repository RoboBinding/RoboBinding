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
import android.view.View.OnClickListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class OnClickAttribute extends AbstractCommandViewAttribute
{
	private final View view;

	public OnClickAttribute(View view)
	{
		this.view = view;
	}

	@Override
	protected void bind(final Command command)
	{
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				command.invoke();
			}
		});
	}
	
	@Override
	public Class<?>[] getPreferredCommandParameterTypes()
	{
		return null;
	}
}
