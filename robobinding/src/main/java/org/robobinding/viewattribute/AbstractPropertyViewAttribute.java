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

import org.robobinding.BindingContext;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.ValueModel;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractPropertyViewAttribute<ViewType extends View, PropertyType> implements PropertyViewAttribute<ViewType>
{
	private PropertyBindingDetails propertyBindingDetails;
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
	public void bindTo(BindingContext bindingContext)
	{
		performValidate();
		performBind(bindingContext);
	}
	
	private void performValidate()
	{
		ViewAttributeValidation validation = new ViewAttributeValidation();
		validate(validation);
		validation.assertNoErrors();
	}

	protected void validate(ViewAttributeValidation validation)
	{
		validation.addErrorIfViewNotSet(view);
		validation.addErrorIfPropertyAttributeValueNotSet(propertyBindingDetails);
	}

	private void performBind(BindingContext bindingContext)
	{
		if (isTwoWayBinding())
			new TwoWayBinder(bindingContext).performBind();
		else
			new OneWayBinder(bindingContext).performBind();
	}

	public boolean isTwoWayBinding()
	{
		return propertyBindingDetails.twoWayBinding;
	}
	
	protected abstract void valueModelUpdated(PropertyType newValue);
	protected abstract void observeChangesOnTheView(final ValueModel<PropertyType> valueModel);
	
	abstract class AbstractPropertyBinder
	{
		private final BindingContext bindingContext;
		protected final PresentationModelAdapter presentationModelAdapter;
		public AbstractPropertyBinder(BindingContext bindingContext)
		{
			this.bindingContext = bindingContext;
			this.presentationModelAdapter = bindingContext.getPresentationModelAdapter();
		}
		
		protected void initializeViewIfRequired(ValueModel<PropertyType> valueModel)
		{
			if (bindingContext.shouldPreInitializeViews())
			{
				valueModelUpdated(valueModel.getValue());
			}
		}
		
		abstract void performBind();
	}
	
	private class OneWayBinder extends AbstractPropertyBinder
	{
		public OneWayBinder(BindingContext bindingContext)
		{
			super(bindingContext);
		}
		@Override
		public void performBind()
		{
			final ValueModel<PropertyType> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
			initializeViewIfRequired(valueModel);
			valueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener(){
				@Override
				public void propertyChanged()
				{
					valueModelUpdated(valueModel.getValue());
				}
			});
		}
	}
	
	private class TwoWayBinder extends AbstractPropertyBinder
	{
		private boolean updatedProgrammatically;
		public TwoWayBinder(BindingContext bindingContext)
		{
			super(bindingContext);
		}
		@Override
		public void performBind()
		{
			ValueModel<PropertyType> valueModel = presentationModelAdapter.getPropertyValueModel(propertyBindingDetails.propertyName);
			valueModel = new PropertyValueModelWrapper(valueModel);
			initializeViewIfRequired(valueModel);
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
