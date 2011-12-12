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

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnTextChangedAttribute extends AbstractCommandViewAttribute
{
	private TextView textView;

	public OnTextChangedAttribute(TextView textView, String commandName)
	{
		super(commandName);
		this.textView = textView;
	}

	@Override
	protected void bind(final Command command)
	{
		textView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				TextChangedEvent event = new TextChangedEvent(textView, start, before, count);
				command.invoke(event);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});
	}

	@Override
	protected Class<?> getPreferredCommandParameterType()
	{
		return TextChangedEvent.class;
	}

}
