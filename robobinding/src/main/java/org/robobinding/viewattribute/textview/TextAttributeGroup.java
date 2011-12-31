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
package org.robobinding.viewattribute.textview;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.viewattribute.AbstractGroupedPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.PropertyViewAttribute;

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
public class TextAttributeGroup extends AbstractGroupedPropertyViewAttribute<TextView>
{
	public static final String TEXT = "text";
	public static final String VALUE_COMMIT_MODE = "valueCommitMode";
	
	private TextAttribute textAttribute;
	private ValueCommitMode valueCommitMode;
	private PropertyBindingDetails propertyBindingDetails;
	
	@Override
	protected void initializeChildViewAttributes()
	{
		assertAttributesArePresent(TEXT);
		propertyBindingDetails = PropertyBindingDetails.createFrom(childAttributes.get(TEXT));
		
		determineValueCommitMode();
		
		textAttribute = new TextAttribute(valueCommitMode);
		textAttribute.setView(view);
		textAttribute.setPropertyBindingDetails(propertyBindingDetails);
	}

	private void determineValueCommitMode()
	{
		if (propertyBindingDetails.twoWayBinding && valueCommitModeSpecified())
			throw new RuntimeException("The valueCommitMode attribute can only be used when a two-way binding text attribute is specified");
		
		if (!valueCommitModeSpecified() || "onChange".equals(valueCommitModeAttributeValue()))
			valueCommitMode = ValueCommitMode.ON_CHANGE;
		else if ("onFocusLost".equals(valueCommitModeAttributeValue()))
			valueCommitMode = ValueCommitMode.ON_FOCUS_LOST;
	}
	
	private String valueCommitModeAttributeValue()
	{
		return childAttributes.get(VALUE_COMMIT_MODE);
	}
	
	private boolean valueCommitModeSpecified()
	{
		return childAttributes.containsKey(VALUE_COMMIT_MODE);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		textAttribute.bind(presentationModelAdapter, context);
	}
	
	public ValueCommitMode getValueCommitMode()
	{
		return valueCommitMode;
	}
	
	private static class TextAttribute extends AbstractMultiTypePropertyViewAttribute<TextView>
	{
		private final ValueCommitMode valueCommitMode;

		public TextAttribute(ValueCommitMode valueCommitMode)
		{
			this.valueCommitMode = valueCommitMode;
		}

		@Override
		protected PropertyViewAttribute<TextView> createPropertyViewAttribute(Class<?> propertyType)
		{
			if (String.class.isAssignableFrom(propertyType))
			{
				return new StringTextAttribute(valueCommitMode);
			}
			else if (CharSequence.class.isAssignableFrom(propertyType))
			{
				return new CharSequenceTextAttribute(valueCommitMode);
			}
			
			throw new RuntimeException("Could not find a suitable text attribute class for property type: " + propertyType);
		}
	}
	
	private abstract static class AbstractCharSequenceTextAttribute<PropertyType extends CharSequence> extends AbstractPropertyViewAttribute<TextView, PropertyType>
	{
		private final ValueCommitMode valueCommitMode;

		public AbstractCharSequenceTextAttribute(ValueCommitMode valueCommitMode)
		{
			this.valueCommitMode = valueCommitMode;
		}

		@Override
		protected void valueModelUpdated(PropertyType newValue)
		{
			view.setText(newValue);
		}

		protected void observeChangesOnTheView(final PropertyValueModel<PropertyType> valueModel)
		{
			if (valueCommitMode == ValueCommitMode.ON_CHANGE)
			{
				view.addTextChangedListener(new TextWatcher() {
					
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
			else
			{
				view.setOnFocusChangeListener(new OnFocusChangeListener() {
					
					@Override
					public void onFocusChange(View v, boolean hasFocus)
					{
						if (!hasFocus)
							updateValueModel(valueModel, view.getText());
					}
				});
			}
		}
		
		protected abstract void updateValueModel(PropertyValueModel<PropertyType> valueModel, CharSequence charSequence);
	}
	
	private static class StringTextAttribute extends AbstractCharSequenceTextAttribute<String>
	{
		public StringTextAttribute(ValueCommitMode valueCommitMode)
		{
			super(valueCommitMode);
		}

		@Override
		protected void updateValueModel(PropertyValueModel<String> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence.toString());
		}
	}

	private static class CharSequenceTextAttribute extends AbstractCharSequenceTextAttribute<CharSequence>
	{
		public CharSequenceTextAttribute(ValueCommitMode valueCommitMode)
		{
			super(valueCommitMode);
		}

		@Override
		protected void updateValueModel(PropertyValueModel<CharSequence> valueModel, CharSequence charSequence)
		{
			valueModel.setValue(charSequence);
		}
	}
}
