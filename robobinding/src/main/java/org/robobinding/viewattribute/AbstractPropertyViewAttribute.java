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
package org.robobinding.viewattribute;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.ValueModel;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractPropertyViewAttribute<ViewType extends View, PropertyType> implements PropertyViewAttribute<ViewType>
{
	private PresentationModelAdapter presentationModelAdapter;
	private PropertyBindingDetails propertyBindingDetails;
	private boolean preInitializeViews;
	protected ViewType view;
	
	@Override
	public void setView(ViewType view)
	{
		this.view = view;
	}
	
	@Override
	public void setAttributeValue(String attributeValue)
	{
		this.propertyBindingDetails = PropertyBindingDetails.createFrom(attributeValue);
	}
	
	protected void setPropertyBindingDetails(PropertyBindingDetails propertyBindingDetails)
	{
		this.propertyBindingDetails = propertyBindingDetails;
	}
	
	@Override
	public void setPreInitializeView(boolean preInitializeView)
	{
		this.preInitializeViews = preInitializeView;
	}
	
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		this.presentationModelAdapter = presentationModelAdapter;
		performBind();
	}
	
	private void performBind()
	{
		if (isTwoWayBinding())
			new TwoWayBinder().performBind();
		else
			new OneWayBinder().performBind();
	}

	public boolean isTwoWayBinding()
	{
		return propertyBindingDetails.twoWayBinding;
	}
	
	void setPresentationModelAdapter(PresentationModelAdapter presentationModelAdapter)
	{
		this.presentationModelAdapter = presentationModelAdapter;
	}
	
	protected void initializeView(ValueModel<PropertyType> valueModel)
	{
		if (preInitializeViews)
			valueModelUpdated(valueModel.getValue());
	}
	
	protected abstract void valueModelUpdated(PropertyType newValue);
	protected abstract void observeChangesOnTheView(final ValueModel<PropertyType> valueModel);
	
	interface PropertyBinder
	{
		void performBind();
	}
	
	private class OneWayBinder implements PropertyBinder
	{
		@Override
		public void performBind()
		{
			final ValueModel<PropertyType> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
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
		private boolean updatedProgrammatically;
		
		@Override
		public void performBind()
		{
			ValueModel<PropertyType> valueModel = presentationModelAdapter.getPropertyValueModel(propertyBindingDetails.propertyName);
			valueModel = new PropertyValueModelWrapper(valueModel);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
			observeChangesOnTheView(valueModel);
		}
		
		private void observeChangesOnTheValueModel(final ValueModel<PropertyType> valueModel)
		{
			valueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener() {
				@Override
				public void propertyChanged()
				{
					if (!updatedProgrammatically)
						valueModelUpdated(valueModel.getValue());
				}
			});
		}
		
		private class PropertyValueModelWrapper implements ValueModel<PropertyType>
		{
			private ValueModel<PropertyType> propertyValueModel;

			public PropertyValueModelWrapper(ValueModel<PropertyType> propertyValueModel)
			{
				this.propertyValueModel = propertyValueModel;
			}

			@Override
			public PropertyType getValue()
			{
				return propertyValueModel.getValue();
			}

			@Override
			public void setValue(PropertyType newValue)
			{
				updatedProgrammatically = true;
				propertyValueModel.setValue(newValue);
				updatedProgrammatically = false;
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
