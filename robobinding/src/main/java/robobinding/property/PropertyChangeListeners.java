/**
 * PropertyChangeListeners.java
 * Nov 13, 2011 Copyright Cheng Wei and Robert Taylor
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

import java.util.Iterator;
import java.util.List;

import robobinding.internal.com_google_common.collect.Lists;
import robobinding.internal.org_apache_commons_lang3.Validate;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyChangeListeners implements Iterable<PropertyChangeListener>
{
	private List<PropertyChangeListener> listeners;
	public PropertyChangeListeners()
	{
		this.listeners = Lists.newArrayList();
	}
	public void add(PropertyChangeListener propertyChangeListener)
	{
		Validate.notNull(propertyChangeListener, "propertyChangeListener cannot be null");
		listeners.add(propertyChangeListener);
	}
	public boolean remove(PropertyChangeListener listener)
	{
		return listeners.remove(listener);
	}
	public boolean contains(PropertyChangeListener listener)
	{
		return listeners.contains(listener);
	}
	public void firePropertyChange()
	{
		for(PropertyChangeListener listener : listeners)
		{
			listener.propertyChanged();
		}
	}
	@Override
	public Iterator<PropertyChangeListener> iterator()
	{
		return listeners.iterator();
	}
	private static PropertyChangeListeners EMPTY = new PropertyChangeListeners();
	public static PropertyChangeListeners empty()
	{
		return EMPTY;
	}
}
