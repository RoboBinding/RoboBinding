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

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.MultiTypePropertyViewAttributeConfig;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class VisibilityAttribute extends AbstractMultiTypePropertyViewAttribute<View>
{
	public VisibilityAttribute(MultiTypePropertyViewAttributeConfig<View> config)
	{
		super(config);
	}

	@Override
	protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType, PropertyViewAttributeConfig<View> config)
	{
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new IntegerVisibilityAttribute(config);
		}
		else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType))
		{
			return new BooleanVisibilityAttribute(config);
		}
		
		throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
	}
	
	static class BooleanVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Boolean>
	{
		public BooleanVisibilityAttribute(PropertyViewAttributeConfig<View> config)
		{
			super(config);
		}

		@Override
		protected void valueModelUpdated(Boolean newValue)
		{
			view.setVisibility(newValue ? View.VISIBLE : View.GONE);
		}
	}
	
	static class IntegerVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Integer>
	{
		public IntegerVisibilityAttribute(PropertyViewAttributeConfig<View> config)
		{
			super(config);
		}

		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			view.setVisibility(newValue);
		}
	}
	
}
