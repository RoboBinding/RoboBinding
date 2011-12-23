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
package org.robobinding.viewattribute.view;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.PropertyViewAttribute;

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
	private final View view;
	private final PropertyBindingDetails propertyBindingDetails;

	public VisibilityAttribute(View view, String attributeValue, boolean preInitializeView)
	{
		this.view = view;
		this.propertyBindingDetails = PropertyBindingDetails.createFrom(attributeValue, preInitializeView);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		PropertyViewAttribute propertyViewAttribute = lookupPropertyViewAttribute(presentationModelAdapter);
		propertyViewAttribute.bind(presentationModelAdapter, context);
	}

	PropertyViewAttribute lookupPropertyViewAttribute(PresentationModelAdapter presentationModelAdapter)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyBindingDetails.propertyName);
		
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new IntegerVisibilityAttribute();
		}
		else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType))
		{
			return new BooleanVisibilityAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
	}
	
	class BooleanVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<Boolean>
	{
		public BooleanVisibilityAttribute()
		{
			super(propertyBindingDetails);
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
			super(propertyBindingDetails);
		}

		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			view.setVisibility(newValue);
		}
	}
}
