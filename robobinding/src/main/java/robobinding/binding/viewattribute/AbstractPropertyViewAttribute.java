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
import robobinding.property.PropertyChangeListener;
import robobinding.property.PropertyValueModel;
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
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(attributeValue, preInitializeView);
		propertyBindingDetails = bindingDetailsBuilder.createPropertyBindingDetails();
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		setPresentationModelAdapter(presentationModelAdapter);
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
	
	protected void observeChangesOnTheValueModel(final PropertyValueModel<T> valueModel)
	{
		valueModel.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChanged()
			{
				valueModelUpdated(valueModel.getValue());
			}
		});
	}
	
	interface PropertyBinder
	{
		void performBind();
	}
	
	class OneWayBinder implements PropertyBinder
	{
		@Override
		public void performBind()
		{
			PropertyValueModel<T> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
		}
	}
	
	class TwoWayBinder implements PropertyBinder
	{
		@Override
		public void performBind()
		{
			PropertyValueModel<T> valueModel = presentationModelAdapter.getPropertyValueModel(propertyBindingDetails.propertyName);
			initializeView(valueModel);
			observeChangesOnTheValueModel(valueModel);
			observeChangesOnTheView(valueModel);
		}
	}
}
