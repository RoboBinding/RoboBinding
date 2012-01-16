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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;

import android.view.View;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewVisibilityAttribute extends AbstractMultiTypePropertyViewAttribute<ListView>
{
	private SubViewVisibility subViewVisibility;
	SubViewVisibilityAttribute(SubViewVisibility subViewVisibility)
	{
		this.subViewVisibility = subViewVisibility;
	}
	@Override
	protected AbstractPropertyViewAttribute<ListView, ?> createPropertyViewAttribute(Class<?> propertyType)
	{
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new IntegerSubViewVisibilityAttribute();
		}
		else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType))
		{
			return new BooleanSubViewVisibilityAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
	}
	
	public static SubViewVisibilityAttribute create(View headerOrFooterView)
	{
		return new SubViewVisibilityAttribute(new SubViewVisibility(headerOrFooterView));
	}
	
	class BooleanSubViewVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<ListView, Boolean>
	{
		@Override
		protected void valueModelUpdated(Boolean newValue)
		{
			if(newValue)
			{
				subViewVisibility.makeVisible();
			}else
			{
				subViewVisibility.makeGone();
			}
		}
	}
	
	class IntegerSubViewVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<ListView, Integer>
	{
		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			subViewVisibility.setVisibility(newValue);
		}
	}
}
