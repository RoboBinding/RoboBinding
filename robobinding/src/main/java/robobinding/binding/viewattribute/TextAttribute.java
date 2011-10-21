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

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.PropertyViewAttribute;
import robobinding.value.ValueModel;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttribute implements PropertyViewAttribute
{
	private final TextView textView;
	private final String attributeValue;

	public TextAttribute(TextView textView, String attributeValue)
	{
		this.textView = textView;
		this.attributeValue = attributeValue;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(attributeValue);
		
		if (propertyType.isAssignableFrom(CharSequence.class))
		{
			new CharSequenceTextAttribute().bind(presentationModelAdapter, context);
		}
		else if (propertyType.isAssignableFrom(String.class))
		{
			new StringTextAttribute().bind(presentationModelAdapter, context);
		}
	}

	class CharSequenceTextAttribute extends AbstractPropertyViewAttribute<CharSequence>
	{
		public CharSequenceTextAttribute()
		{
			super(attributeValue);
		}
		
		@Override
		protected void valueModelUpdated(CharSequence newValue)
		{
			textView.setText(newValue);
		}

		@Override
		protected void observeChangesOnTheView(final ValueModel<CharSequence> valueModel)
		{
			textView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					valueModel.setValue(s);
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
	
	class StringTextAttribute extends AbstractPropertyViewAttribute<String>
	{
		public StringTextAttribute()
		{
			super(attributeValue);
		}

		@Override
		protected void valueModelUpdated(String newValue)
		{
			textView.setText(newValue);
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
}
