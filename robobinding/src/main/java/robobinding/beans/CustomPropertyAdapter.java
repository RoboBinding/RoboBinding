/**
 * CustomPropertyAdapter.java
 * Oct 14, 2011 Copyright Cheng Wei and Robert Taylor
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
class CustomPropertyAdapter<T> extends AbstractPropertyAdapter<T>
{
	private String propertyName;
	private CustomPropertyDescriptor<T> descriptor;
	private ExtendedPropertyChangeSupport propertyChangeSupport;
	public CustomPropertyAdapter(Object bean, String propertyName, CustomPropertyDescriptor<T> descriptor)
	{
		super(bean);
		this.propertyName = propertyName;
		this.descriptor = descriptor;
		initializePropertyChangeListener();
	}
	private void initializePropertyChangeListener()
	{
		propertyChangeSupport = new ExtendedPropertyChangeSupport(bean);
		descriptor.addValueChangeListener(new PropertyChangeListenerImpl());
	}
	@Override
	public T getValue()
	{
		return descriptor.getValue();
	}
	@Override
	public void setValue(T newValue)
	{
		descriptor.setValue(newValue);
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		super.addValueChangeListener(listener);
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		super.removeValueChangeListener(listener);
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	@Override
	public Class<?> getPropertyType()
	{
		return descriptor.getPropertyType();
	}
	@Override
	public void checkReadWriteProperty(boolean isReadWriteProperty)
	{
		checkReadable();
		if(isReadWriteProperty)
		{
			checkWritable();
		}
	}
	private void checkWritable()
	{
		if(!descriptor.isWritable())
		{
			throw new RuntimeException(propertyDescription()+" is not writable");
		}
	}
	private void checkReadable()
	{
		if(!descriptor.isReadable())
		{
			throw new RuntimeException(propertyDescription() +" is not readable");
		}
	}
	private String propertyDescription()
	{
		return PropertyAccessor.describeProperty(bean.getClass(), propertyName);
	}
	@Override
	protected String getPropertyName()
	{
		return propertyName;
	}
	private class PropertyChangeListenerImpl implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent event)
		{
			propertyChangeSupport.firePropertyChange(propertyName, event.getOldValue(), event.getNewValue());
		}
	}
}
