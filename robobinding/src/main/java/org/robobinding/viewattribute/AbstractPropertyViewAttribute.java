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
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.ValueModel;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractPropertyViewAttribute<ViewType extends View, PropertyType> implements PropertyViewAttribute<ViewType>
{
	protected ViewType view;
	private ValueModelAttribute attribute;

	public AbstractPropertyViewAttribute(PropertyViewAttributeConfig<ViewType> config)
	{
		this.view = config.getView();
		this.attribute = config.getAttribute();
	}
	
	protected void postConstruct()
	{
	}

	@Override
	public final void preinitializeView(BindingContext bindingContext)
	{
		PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
		ValueModel<PropertyType> valueModel = presentationModelAdapter.getPropertyValueModel(attribute.getPropertyName());
		valueModelUpdated(valueModel.getValue());
	}
	
	@Override
	public final void bindTo(BindingContext bindingContext)
	{
		try
		{
			performBind(bindingContext);
		}catch(RuntimeException e)
		{
			throw new AttributeBindingException(attribute.getName(), e);
		}
	}
	
	private void performBind(BindingContext bindingContext)
	{
		if (attribute.isTwoWayBinding())
			new TwoWayBinder(bindingContext).performBind();
		else
			new OneWayBinder(bindingContext).performBind();
	}
	
	protected abstract void valueModelUpdated(PropertyType newValue);
	protected abstract void observeChangesOnTheView(final ValueModel<PropertyType> valueModel);
	
	abstract class AbstractPropertyBinder
	{
		protected final PresentationModelAdapter presentationModelAdapter;
		public AbstractPropertyBinder(BindingContext bindingContext)
		{
			this.presentationModelAdapter = bindingContext.getPresentationModelAdapter();
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
			final ValueModel<PropertyType> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(attribute.getPropertyName());
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
			ValueModel<PropertyType> valueModel = presentationModelAdapter.getPropertyValueModel(attribute.getPropertyName());
			valueModel = new PropertyValueModelWrapper(valueModel);
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
