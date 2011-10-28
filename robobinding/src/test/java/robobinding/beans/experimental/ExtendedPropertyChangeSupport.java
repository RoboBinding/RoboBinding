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
package robobinding.beans.experimental;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;

/**
 * Differs from its superclass {@link PropertyChangeSupport} in that it can
 * check for changed values using {@code #equals} or {@code ==}. Useful if you
 * want to ensure that a {@code PropertyChangeEvent} is fired if the old and new
 * value are not the same but if they are equal.
 * <p>
 */
public final class ExtendedPropertyChangeSupport extends PropertyChangeSupport
{
	private static final long serialVersionUID = 4908378981887614204L;

	private final Object bean;

	private final boolean checkIdentityDefault;

	public ExtendedPropertyChangeSupport(Object bean)
	{
		this(bean, false);
	}

	public ExtendedPropertyChangeSupport(Object bean, boolean checkIdentityDefault)
	{
		super(bean);
		this.bean = bean;
		this.checkIdentityDefault = checkIdentityDefault;
	}
	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		firePropertyChange(propertyName, oldValue, newValue, checkIdentityDefault);
	}
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		if(oldValue != null && oldValue == newValue)
		{
			return;
		}
		firePropertyChange0(propertyName, oldValue, newValue, checkIdentity);
	}


	private void firePropertyChange0(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		if(checkIdentity)
		{
			firePropertyChangeWithoutEqualsChecking(new PropertyChangeEvent(bean, propertyName, oldValue, newValue));
		} else
		{
			super.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	private void firePropertyChangeWithoutEqualsChecking(PropertyChangeEvent event)
	{
		PropertyChangeListener[] listeners;
		synchronized (this)
		{
			listeners = getPropertyChangeListeners();
		}
		String propertyName = event.getPropertyName();
		for(PropertyChangeListener listener : listeners)
		{
			if (listener instanceof PropertyChangeListenerProxy)
			{
				PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) listener;
				if (proxy.getPropertyName().equals(propertyName))
				{
					proxy.propertyChange(event);
				}
			} else
			{
				listener.propertyChange(event);
			}
		}
	}
	public void removeAllListeners()
	{
		PropertyChangeListener[] propertyChangeListeners = getPropertyChangeListeners();
		for(PropertyChangeListener propertyChangeListener : propertyChangeListeners)
		{
			removePropertyChangeListener(propertyChangeListener);
		}
	}
}
