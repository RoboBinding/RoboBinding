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
package org.robobinding.property;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class PropertyWrapper<T> implements Property<T>
{
	private Property<T> property;
	public PropertyWrapper(Property<T> property)
	{
		this.property = property;
	}
	@Override
	public T getValue()
	{
		return property.getValue();
	}

	@Override
	public void setValue(T newValue)
	{
		property.setValue(newValue);
	}

	@Override
	public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		property.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		property.removePropertyChangeListener(listener);
	}

	@Override
	public Class<?> getPropertyType()
	{
		return property.getPropertyType();
	}

	@Override
	public void checkReadWriteProperty(boolean isReadWriteProperty)
	{
		property.checkReadWriteProperty(isReadWriteProperty);
	}

	@Override
	public PropertyAccessor<T> getPropertyAccessor()
	{
		return property.getPropertyAccessor();
	}

}
