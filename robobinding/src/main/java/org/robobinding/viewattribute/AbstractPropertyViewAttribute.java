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
import org.robobinding.property.PropertyValueModel;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractPropertyViewAttribute<S, T extends View> extends TypeReference<S> implements PropertyViewAttribute<S, T>
{
	private PresentationModelAdapter presentationModelAdapter;
	private PropertyBindingDetails propertyBindingDetails;
	protected T view;
	private String propertyName;
	
	@Override
	public void setView(T view)
	{
		this.view = view;
	}
	
	@Override
	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}
	
	@Override
	public void setPreInitializeViews(boolean preInitializeViews)
	{
		propertyBindingDetails = PropertyBindingDetails.createFrom(propertyName, preInitializeViews);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<S> getPropertyType()
	{
		return (Class<S>)getType().getClass();
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
	
	protected abstract void valueModelUpdated(S newValue);
	protected abstract void observeChangesOnTheView(final PropertyValueModel<S> valueModel);
	
	protected void initializeView(PropertyValueModel<S> valueModel)
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
			final PropertyValueModel<S> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
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
			PropertyValueModel<S> valueModel = presentationModelAdapter.getPropertyValueModel(propertyBindingDetails.propertyName);
			valueModel = new PropertyValueModelWrapper(valueModel);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
			observeChangesOnTheView(valueModel);
		}
		
		private void observeChangesOnTheValueModel(final PropertyValueModel<S> valueModel)
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
		
		private class PropertyValueModelWrapper implements PropertyValueModel<S>
		{
			private PropertyValueModel<S> propertyValueModel;

			public PropertyValueModelWrapper(PropertyValueModel<S> propertyValueModel)
			{
				this.propertyValueModel = propertyValueModel;
			}

			@Override
			public S getValue()
			{
				return propertyValueModel.getValue();
			}

			@Override
			public void setValue(S newValue)
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
