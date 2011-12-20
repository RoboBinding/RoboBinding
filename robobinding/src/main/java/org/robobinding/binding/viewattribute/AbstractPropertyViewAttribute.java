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
package org.robobinding.binding.viewattribute;

import org.robobinding.binding.PropertyViewAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.PropertyValueModel;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractPropertyViewAttribute<T> implements PropertyViewAttribute
{
	private PresentationModelAdapter presentationModelAdapter;
	private final PropertyBindingDetails propertyBindingDetails;
	
	public AbstractPropertyViewAttribute(PropertyBindingDetails propertyBindingDetails)
	{
		this.propertyBindingDetails = propertyBindingDetails;
	}
	
	public AbstractPropertyViewAttribute(String attributeValue, boolean preInitializeView)
	{
		propertyBindingDetails = PropertyBindingDetails.createFrom(attributeValue, preInitializeView);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		this.presentationModelAdapter = presentationModelAdapter;
		performBind();
	}

	private void performBind()
	{
		if (propertyBindingDetails.twoWayBinding)
			performTwoWayBinding();
		else
			performOneWayBinding();
	}

	void performOneWayBinding()
	{
		new OneWayBinder().performBind();
	}
	
	void performTwoWayBinding()
	{
		new TwoWayBinder().performBind();
	}
	
	void setPresentationModelAdapter(PresentationModelAdapter presentationModelAdapter)
	{
		this.presentationModelAdapter = presentationModelAdapter;
	}
	
	protected abstract void valueModelUpdated(T newValue);
	protected abstract void observeChangesOnTheView(final PropertyValueModel<T> valueModel);
	
	protected void initializeView(PropertyValueModel<T> valueModel)
	{
		if (propertyBindingDetails.preInitializeView)
			valueModelUpdated(valueModel.getValue());
	}
	
	interface PropertyBinder
	{
		void performBind();
	}
	
	private class OneWayBinder implements PropertyBinder
	{
		@Override
		public void performBind()
		{
			final PropertyValueModel<T> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
			initializeView(valueModel);
			valueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener(){
				@Override
				public void propertyChanged()
				{
					valueModelUpdated(valueModel.getValue());
				}
			});
		}
	}
	
	private class TwoWayBinder implements PropertyBinder
	{
		private boolean ignoreNextValueModelUpdate;
		
		@Override
		public void performBind()
		{
			PropertyValueModel<T> valueModel = presentationModelAdapter.getPropertyValueModel(propertyBindingDetails.propertyName);
			valueModel = new PropertyValueModelWrapper(valueModel);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
			observeChangesOnTheView(valueModel);
		}
		
		private void observeChangesOnTheValueModel(final PropertyValueModel<T> valueModel)
		{
			valueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener() {
				@Override
				public void propertyChanged()
				{
					if (ignoreNextValueModelUpdate)
					{
						ignoreNextValueModelUpdate = false;
						return;
					}
					
					if (propertyBindingDetails.twoWayBinding)
						ignoreNextValueModelUpdate = true;
					
					valueModelUpdated(valueModel.getValue());
				}
			});
		}
		
		private class PropertyValueModelWrapper implements PropertyValueModel<T>
		{
			private PropertyValueModel<T> propertyValueModel;

			public PropertyValueModelWrapper(PropertyValueModel<T> propertyValueModel)
			{
				this.propertyValueModel = propertyValueModel;
			}

			@Override
			public T getValue()
			{
				return propertyValueModel.getValue();
			}

			@Override
			public void setValue(T newValue)
			{
				ignoreNextValueModelUpdate = true;
				propertyValueModel.setValue(newValue);
			}

			@Override
			public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener)
			{
				propertyValueModel.addPropertyChangeListener(listener);
			}

			@Override
			public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener)
			{
				propertyValueModel.removePropertyChangeListener(listener);
			}
		}
	}
}
