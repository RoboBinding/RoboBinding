/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.textview;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.MultiTypePropertyViewAttributeConfig;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractTextAttribute<T extends TextView> extends AbstractMultiTypePropertyViewAttribute<T>
{
	public AbstractTextAttribute(MultiTypePropertyViewAttributeConfig<T> config)
	{
		super(config);
	}

	@Override
	protected AbstractPropertyViewAttribute<T, ?> createPropertyViewAttribute(Class<?> propertyType, PropertyViewAttributeConfig<T> config)
	{
		if (String.class.isAssignableFrom(propertyType))
		{
			return createNewStringAttribute(config);
		} 
		else if (CharSequence.class.isAssignableFrom(propertyType))
		{
			return createNewCharSequenceAttribute(config);
		}

		return null;
	}

	protected abstract AbstractPropertyViewAttribute<T, ?> createNewStringAttribute(PropertyViewAttributeConfig<T> config);
	protected abstract AbstractPropertyViewAttribute<T, ?> createNewCharSequenceAttribute(PropertyViewAttributeConfig<T> config);
}
