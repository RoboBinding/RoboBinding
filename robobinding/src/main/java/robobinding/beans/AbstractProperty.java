/**
 * AbstractProperty.java
 * Oct 27, 2011 Copyright Cheng Wei and Robert Taylor
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
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractProperty<T> implements ValueModel<T>
{
	protected final Object bean;
	protected final PropertyAccessor<T> propertyAccessor;
	protected AbstractProperty(Object bean, PropertyAccessor<T> propertyAccessor)
	{
		this.bean = bean;
		this.propertyAccessor = propertyAccessor;
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
	protected ObservableProperties getObservableBean()
	{
		if(bean instanceof ObservableProperties)
		{
			ObservableProperties observableBean = (ObservableProperties)bean;
			return observableBean;
		}
		throw new RuntimeException("The property changes of '"+bean.getClass().getName()+"' can not be observed, as it is not a subclass of 'ObservableProperties'");
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
}
