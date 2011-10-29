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

import robobinding.binding.PropertyViewAttribute;
import robobinding.presentationmodel.PresentationModelAdapter;
import robobinding.property.PropertyValueModel;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
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
	private final PropertyBinding propertyBinding;

	public TextAttribute(TextView textView, String attributeValue)
	{
		this.textView = textView;
		this.propertyBinding = new PropertyBinding(attributeValue);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyBinding.propertyName);
		
		if (SpannedString.class.isAssignableFrom(propertyType))
		{
			new SpannedStringTextAttribute().bind(presentationModelAdapter, context);
		}
		else if (SpannableString.class.isAssignableFrom(propertyType))
		{
			new SpannableStringTextAttribute().bind(presentationModelAdapter, context);
		}
		else if (SpannableStringBuilder.class.isAssignableFrom(propertyType))
		{
			new SpannableStringBuilderTextAttribute().bind(presentationModelAdapter, context);
		}
		else if (CharSequence.class.isAssignableFrom(propertyType))
		{
			new CharSequenceTextAttribute().bind(presentationModelAdapter, context);
		}
		else if (String.class.isAssignableFrom(propertyType))
		{
			new StringTextAttribute().bind(presentationModelAdapter, context);
		}
	}

	abstract class AbstractCharSequenceTextAttribute<T extends CharSequence> extends AbstractPropertyViewAttribute<T>
	{
		private boolean suppressNextViewUpdate = false;
		
		public AbstractCharSequenceTextAttribute()
		{
			super(propertyBinding);
		}
		
		@Override
		protected void valueModelUpdated(T newValue)
		{
			if (suppressNextViewUpdate)
			{
				suppressNextViewUpdate = false;
				return;
			}
			textView.setText(newValue);
		}

		protected void suppressNextViewUpdate()
		{
			suppressNextViewUpdate = true;
		}
		
		protected void observeChangesOnTheView(final PropertyValueModel<T> valueModel)
		{
			textView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					updateValueModel(valueModel, s);
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
		
		protected abstract void updateValueModel(PropertyValueModel<T> valueModel, CharSequence charSequence);
	}
	
	class StringTextAttribute extends AbstractCharSequenceTextAttribute<String>
	{
		@Override
		protected void updateValueModel(PropertyValueModel<String> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence.toString());
		}
	}

	class SpannedStringTextAttribute extends AbstractCharSequenceTextAttribute<SpannedString>
	{
		@Override
		protected void updateValueModel(PropertyValueModel<SpannedString> valueModel, CharSequence charSequence)
		{
			suppressNextViewUpdate();
			valueModel.setValue(new SpannedString(charSequence));
		}
	}

	public class CharSequenceTextAttribute extends AbstractCharSequenceTextAttribute<CharSequence>
	{
		@Override
		protected void updateValueModel(PropertyValueModel<CharSequence> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence);
		}
	}
	
	public class SpannableStringBuilderTextAttribute extends AbstractCharSequenceTextAttribute<SpannableStringBuilder>
	{
		@Override
		protected void updateValueModel(PropertyValueModel<SpannableStringBuilder> valueModel, CharSequence charSequence)
		{
			suppressNextViewUpdate();
			valueModel.setValue(new SpannableStringBuilder(charSequence));
		}
	}
	
	public class SpannableStringTextAttribute extends AbstractCharSequenceTextAttribute<SpannableString>
	{
		@Override
		protected void updateValueModel(PropertyValueModel<SpannableString> valueModel, CharSequence charSequence)
		{
			suppressNextViewUpdate();
			valueModel.setValue(new SpannableString(charSequence));
		}
	}
}
