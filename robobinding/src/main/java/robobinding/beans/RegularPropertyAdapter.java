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
package robobinding.beans;


import java.beans.PropertyChangeListener;

import robobinding.presentationmodel.ObservableProperties;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
class RegularPropertyAdapter<T> implements PropertyAdapter<T>, ValueModel<T>
{
	private PropertyAccessor<T> propertyAccessor;
	private final Object bean;
	public RegularPropertyAdapter(Object bean, String propertyName, boolean isReadWriteProperty)
	{
		this.bean = bean;
		initializePropertyAccessor(propertyName, isReadWriteProperty);
	}
	private void initializePropertyAccessor(String propertyName, boolean isReadWriteProperty)
	{
		propertyAccessor = new PropertyAccessor<T>(propertyName, bean.getClass());
		propertyAccessor.checkReadable();
		if(isReadWriteProperty)
		{
			propertyAccessor.checkWritable();
		}
	}
	@Override
	public ValueModel<T> getPropertyValueModel()
	{
		return this;
	}
	@Override
	public T getValue()
	{
		return propertyAccessor.getValue(bean);
	}
	@Override
	public void setValue(T newValue)
	{
		propertyAccessor.setValue(bean, newValue);
	}
	public Class<?> getPropertyType()
	{
		return propertyAccessor.getPropertyType();
	}
	public void checkReadWriteProperty(boolean isReadWriteProperty)
	{
		propertyAccessor.checkReadable();
		if(isReadWriteProperty)
		{
			propertyAccessor.checkWritable();
		}
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		ObservableProperties observableBean = getObservableBean();
		observableBean.addPropertyChangeListener(propertyAccessor.getPropertyName(), listener);
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		if(bean instanceof ObservableProperties)
		{
			ObservableProperties observableBean = (ObservableProperties)bean;
			observableBean.removePropertyChangeListener(propertyAccessor.getPropertyName(), listener);
		}
	}
	private ObservableProperties getObservableBean()
	{
		if(bean instanceof ObservableProperties)
		{
			ObservableProperties observableBean = (ObservableProperties)bean;
			return observableBean;
		}
		throw new RuntimeException("The property changes of '"+bean.getClass().getName()+"' can not be observed, as it is not a subclass of 'ObservableProperties'");
	}
	@Override
	public String toString()
	{
		return getClass().getName() + "[" + paramString() + "]";
	}
	private String paramString()
	{
		return propertyAccessor.describeProperty(bean);
	}
}
