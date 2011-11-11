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
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
	private final ValueCommitMode valueCommitMode;
	private final PropertyBinding propertyBinding;

	public TextAttribute(TextView textView, PropertyBinding propertyBinding, ValueCommitMode valueCommitMode)
	{
		this.textView = textView;
		this.valueCommitMode = valueCommitMode;
		this.propertyBinding = propertyBinding;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		PropertyViewAttribute propertyViewAttribute = lookupPropertyViewAttribute(presentationModelAdapter);
		propertyViewAttribute.bind(presentationModelAdapter, context);
	}

	PropertyViewAttribute lookupPropertyViewAttribute(PresentationModelAdapter presentationModelAdapter)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyBinding.propertyName);
		
		if (String.class.isAssignableFrom(propertyType))
		{
			return new StringTextAttribute();
		}
		else if (CharSequence.class.isAssignableFrom(propertyType))
		{
			return new CharSequenceTextAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
	}
	
	public ValueCommitMode getValueCommitMode()
	{
		return valueCommitMode;
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
			if (valueCommitMode == ValueCommitMode.ON_CHANGE)
			{
				textView.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count)
					{
						suppressNextViewUpdate();
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
			else
			{
				textView.setOnFocusChangeListener(new OnFocusChangeListener() {
					
					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
						if (!hasFocus)
							updateValueModel(valueModel, textView.getText());
					}
				});
			}
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

	class CharSequenceTextAttribute extends AbstractCharSequenceTextAttribute<CharSequence>
	{
		@Override
		protected void updateValueModel(PropertyValueModel<CharSequence> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence);
		}
	}
}
