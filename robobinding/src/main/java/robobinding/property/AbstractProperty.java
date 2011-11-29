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
package robobinding.property;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractProperty<T> implements PropertyValueModel<T>
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
	public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		ObservableProperties observableBean = getObservableBean();
		observableBean.addPropertyChangeListener(propertyAccessor.getPropertyName(), listener);
	}

	@Override
	public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener)
	{
		if(isObservableBean())
		{
			ObservableProperties observableBean = getObservableBean();
			observableBean.removePropertyChangeListener(propertyAccessor.getPropertyName(), listener);
		}
	}
	protected boolean isObservableBean()
	{
		return bean instanceof ObservableProperties;
	}
	protected ObservableProperties getObservableBean()
	{
		if(bean instanceof ObservableProperties)
		{
			return (ObservableProperties)bean;
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
	@Override
	public String toString()
	{
		return propertyAccessor.toString();
	}
}
