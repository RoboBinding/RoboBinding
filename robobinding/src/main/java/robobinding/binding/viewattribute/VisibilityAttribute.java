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

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.PropertyViewAttribute;
import robobinding.value.ValueModel;
import android.view.View;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class VisibilityAttribute implements PropertyViewAttribute
{
	private View view;
	private final String attributeValue;

	public VisibilityAttribute(View view, String attributeValue)
	{
		this.view = view;
		this.attributeValue = attributeValue;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(attributeValue);
	
		if (propertyType.isAssignableFrom(Integer.class))
		{
			new IntegerVisibilityAttribute(attributeValue).bind(presentationModelAdapter);
		}
		else if (propertyType.isAssignableFrom(Boolean.class))
		{
			new BooleanVisibilityAttribute(attributeValue).bind(presentationModelAdapter);
		}
	}

	class BooleanVisibilityAttribute extends AbstractPropertyViewAttribute<Boolean>
	{
		public BooleanVisibilityAttribute(String attributeValue)
		{
			super(attributeValue);
		}

		@Override
		protected void initializeView(ValueModel<Boolean> valueModel)
		{
			view.setVisibility(valueModel.getValue() ? View.VISIBLE : View.GONE);
		}

		@Override
		protected void observeChangesOnTheValueModel(final ValueModel<Boolean> valueModel)
		{
			valueModel.addValueChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					view.setVisibility(valueModel.getValue() ? View.VISIBLE : View.GONE);
				}
			});
		}

		@Override
		protected void observeChangesOnTheView(ValueModel<Boolean> valueModel)
		{
			throw new RuntimeException("Visibility only supports one-way binding");
		}
	}
	
	class IntegerVisibilityAttribute extends AbstractPropertyViewAttribute<Integer>
	{
		public IntegerVisibilityAttribute(String attributeValue)
		{
			super(attributeValue);
		}

		@Override
		protected void initializeView(ValueModel<Integer> valueModel)
		{
			view.setVisibility(valueModel.getValue());
		}

		@Override
		protected void observeChangesOnTheValueModel(final ValueModel<Integer> valueModel)
		{
			valueModel.addValueChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					view.setVisibility(valueModel.getValue());
				}
			});
		}

		@Override
		protected void observeChangesOnTheView(ValueModel<Integer> valueModel)
		{
			throw new RuntimeException("Visibility only supports one-way binding");
		}

	}
}
