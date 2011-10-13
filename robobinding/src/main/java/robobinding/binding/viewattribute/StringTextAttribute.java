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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import robobinding.value.ValueModel;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class StringTextAttribute extends AbstractPropertyViewAttribute<String>
{
	private final TextView textView;

	public StringTextAttribute(TextView textView)
	{
		this.textView = textView;
	}

	@Override
	protected void initializeView(ValueModel<String> valueModel)
	{
		textView.setText(valueModel.getValue());
	}

	@Override
	protected void observeChangesOnTheValueModel(final ValueModel<String> valueModel)
	{
		valueModel.addValueChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				textView.setText(valueModel.getValue());
			}
		});
	}

	@Override
	protected void observeChangesOnTheView(final ValueModel<String> valueModel)
	{
		textView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				valueModel.setValue(s.toString());
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

}
