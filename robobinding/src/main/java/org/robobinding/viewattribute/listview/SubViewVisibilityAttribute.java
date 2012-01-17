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
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.adapterview.SubViewVisibility;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewVisibilityAttribute extends AbstractMultiTypePropertyViewAttribute<View>
{
	private SubViewVisibility visibility;
	public SubViewVisibilityAttribute(SubViewVisibility headerOrFooterVisibility)
	{
		this.visibility = headerOrFooterVisibility;
	}
	@Override
	protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType)
	{
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new IntegerHeaderOrFooterVisibilityAttribute();
		}
		else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType))
		{
			return new BooleanHeaderOrFooterVisibilityAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
	}
	
	public static SubViewVisibilityAttribute create(SubViewVisibility subViewVisibility)
	{
		return new SubViewVisibilityAttribute(subViewVisibility);
	}
	
	class BooleanHeaderOrFooterVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Boolean>
	{
		@Override
		protected void valueModelUpdated(Boolean newValue)
		{
			if(newValue)
			{
				visibility.makeVisible();
			}else
			{
				visibility.makeGone();
			}
		}
	}
	
	class IntegerHeaderOrFooterVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Integer>
	{
		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			visibility.setVisibility(newValue);
		}
	}

}
