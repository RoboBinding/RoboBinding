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
	private AbstractBindingProperty bindingProperty;
	private boolean withAlwaysPreInitializingView;
	
	public AbstractPropertyViewAttribute()
	{
		this(false);
	}
	
	public AbstractPropertyViewAttribute(boolean withAlwaysPreInitializingView)
	{
		this.withAlwaysPreInitializingView = withAlwaysPreInitializingView;
	}
	
	public void initialize(PropertyViewAttributeConfig<ViewType> config)
	{
		this.view = config.getView();
		this.attribute = config.getAttribute();
		initializeProperty();
	}

	private void initializeProperty()
	{
		if (attribute.isTwoWayBinding())
		{
			bindingProperty = new TwoWayBindingProperty();
		}else
		{
			bindingProperty = new OneWayBindingProperty();
		}
	}

	@Override
	public void bindTo(BindingContext bindingContext)
	{
		try
		{
			bindingProperty.performBind(bindingContext);
			if(withAlwaysPreInitializingView)
			{
				bindingProperty.preInitializeView(bindingContext);
			}
		}catch(RuntimeException e)
		{
			throw new AttributeBindingException(attribute.getName(), e);
		}
	}
	
	protected abstract void valueModelUpdated(PropertyType newValue);
	protected abstract void observeChangesOnTheView(final ValueModel<PropertyType> valueModel);
	
	@Override
	public void preInitializeView(BindingContext bindingContext)
	{
		if(withAlwaysPreInitializingView)
		{
			return;
		}
		
		try
		{
			bindingProperty.preInitializeView(bindingContext);
		}catch(RuntimeException e)
		{
			throw new AttributeBindingException(attribute.getName(), e);
		}
	}

	private abstract class AbstractBindingProperty
	{
		public void preInitializeView(BindingContext bindingContext)
		{
			ValueModel<PropertyType> valueModel = getPropertyValueModel(bindingContext);
			valueModelUpdated(valueModel.getValue());
		}
		
		public abstract ValueModel<PropertyType> getPropertyValueModel(BindingContext bindingContext);

		public abstract void performBind(BindingContext bindingContext);
	}
	
	private class OneWayBindingProperty extends AbstractBindingProperty
	{
		@Override
		public void performBind(BindingContext bindingContext)
		{
			final ValueModel<PropertyType> valueModel = getPropertyValueModel(bindingContext);
			valueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener(){
				@Override
				public void propertyChanged()
				{
					valueModelUpdated(valueModel.getValue());
				}
			});
		}
		
		@Override
		public ValueModel<PropertyType> getPropertyValueModel(BindingContext bindingContext)
		{
			PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
			return presentationModelAdapter.getReadOnlyPropertyValueModel(attribute.getPropertyName());
		}
	}
	
	private class TwoWayBindingProperty extends AbstractBindingProperty
	{
		private boolean updatedProgrammatically;
		
		@Override
		public void performBind(BindingContext bindingContext)
		{
			ValueModel<PropertyType> valueModel = getPropertyValueModel(bindingContext);
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
		
		@Override
		public ValueModel<PropertyType> getPropertyValueModel(BindingContext bindingContext)
		{
			PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
			return presentationModelAdapter.getPropertyValueModel(attribute.getPropertyName());
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
