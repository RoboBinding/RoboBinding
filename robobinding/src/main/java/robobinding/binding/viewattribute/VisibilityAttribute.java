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
import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class VisibilityAttribute implements PropertyViewAttribute
{
	private View view;
	private final PropertyBinding propertyBinding;

	public VisibilityAttribute(View view, String attributeValue)
	{
		this.view = view;
		this.propertyBinding = new PropertyBinding(attributeValue);
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
		
		if (propertyType.isAssignableFrom(int.class) || propertyType.isAssignableFrom(Integer.class))
		{
			return new IntegerVisibilityAttribute();
		}
		else if (propertyType.isAssignableFrom(boolean.class) || propertyType.isAssignableFrom(Boolean.class))
		{
			return new BooleanVisibilityAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
	}
	
	class BooleanVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<Boolean>
	{
		public BooleanVisibilityAttribute()
		{
			super(propertyBinding);
		}

		@Override
		protected void valueModelUpdated(Boolean newValue)
		{
			view.setVisibility(newValue ? View.VISIBLE : View.GONE);
		}
	}
	
	class IntegerVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<Integer>
	{
		public IntegerVisibilityAttribute()
		{
			super(propertyBinding);
		}

		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			view.setVisibility(newValue);
		}
	}
}
