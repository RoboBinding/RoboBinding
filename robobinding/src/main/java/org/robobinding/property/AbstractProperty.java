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
abstract class AbstractProperty<T> implements PropertyValueModel<T>
{
	private final ObservableBean observableBean;
	private final PropertyAccessor<T> propertyAccessor;

	protected AbstractProperty(ObservableBean observableBean, PropertyAccessor<T> propertyAccessor)
	{
		this.observableBean = observableBean;
		this.propertyAccessor = propertyAccessor;
	}

	@Override
	public T getValue()
	{
		return propertyAccessor.getValue(getBean());
	}

	@Override
	public void setValue(T newValue)
	{
		propertyAccessor.setValue(getBean(), newValue);
	}

	@Override
	public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		observableBean.addPropertyChangeListener(propertyAccessor.getPropertyName(), listener);
	}

	@Override
	public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		observableBean.removePropertyChangeListener(propertyAccessor.getPropertyName(), listener);
	}

	@Override
	public Class<?> getPropertyType()
	{
		return propertyAccessor.getPropertyType();
	}

	@Override
	public void checkReadWriteProperty(boolean isReadWriteProperty)
	{
		propertyAccessor.checkReadable();
		if (isReadWriteProperty)
		{
			propertyAccessor.checkWritable();
		}
	}

	public PropertyAccessor<T> getPropertyAccessor()
	{
		return propertyAccessor;
	}
	
	protected final Object getBean()
	{
		return observableBean.getBean();
	}
	
	@Override
	public String toString()
	{
		return propertyAccessor.toString();
	}
}
