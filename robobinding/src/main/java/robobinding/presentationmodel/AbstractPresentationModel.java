/**
 * AbstractPresentationModel.java
 * 11 Oct 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.presentationmodel;

import java.beans.PropertyChangeListener;

import robobinding.beans.ExtendedPropertyChangeSupport;

/**
 * @since 1.0
 * @author Robert Taylor
 *
 */
public abstract class AbstractPresentationModel implements ObservableProperties
{
	private ExtendedPropertyChangeSupport propertyChangeSupport = new ExtendedPropertyChangeSupport(this);;
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue)
	{
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected void firePropertyChange(String propertyName, int oldValue, int newValue)
	{
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	
}
