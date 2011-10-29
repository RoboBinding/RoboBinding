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
	private final String attributeValue;

	public VisibilityAttribute(View view, String attributeValue)
	{
		this.view = view;
		this.attributeValue = attributeValue;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(attributeValue);
	
		if (propertyType.isAssignableFrom(Integer.class))
		{
			new IntegerVisibilityAttribute().bind(presentationModelAdapter, context);
		}
		else if (propertyType.isAssignableFrom(Boolean.class))
		{
			new BooleanVisibilityAttribute().bind(presentationModelAdapter, context);
		}
	}

	class BooleanVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<Boolean>
	{
		public BooleanVisibilityAttribute()
		{
			super(attributeValue);
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
			super(attributeValue);
		}

		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			view.setVisibility(newValue);
		}
	}
}
