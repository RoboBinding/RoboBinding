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

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeSupport
{
	private Object bean;
	private Set<String> availablePropertyNames;
	private Map<String, PresentationModelPropertyChangeListeners> propertyToItsChangeListeners;
	public PresentationModelPropertyChangeSupport(Object bean)
	{
		Validate.notNull(bean);
		
		this.bean = bean;
		initializeAvailableProperties();
		
		propertyToItsChangeListeners = Maps.newHashMap();
	}

	private void initializeAvailableProperties()
	{
		availablePropertyNames = Sets.newHashSet(
				PropertyUtils.getPropertyNames(bean.getClass()));
	}

	public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		validatePropertyName(propertyName);
		if(!propertyToItsChangeListeners.containsKey(propertyName))
		{
			propertyToItsChangeListeners.put(propertyName, new PresentationModelPropertyChangeListeners());
		}
		
		PresentationModelPropertyChangeListeners listeners = propertyToItsChangeListeners.get(propertyName);
		listeners.add(listener);
	}

	public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		if(propertyToItsChangeListeners.containsKey(propertyName))
		{
			PresentationModelPropertyChangeListeners listeners = propertyToItsChangeListeners.get(propertyName);
			listeners.remove(listener);
		}
	}

	PresentationModelPropertyChangeListeners getPropertyChangeListeners(String propertyName)
	{
		if(propertyToItsChangeListeners.containsKey(propertyName))
		{
			return propertyToItsChangeListeners.get(propertyName);
		}
		return PresentationModelPropertyChangeListeners.empty();
	}

	public void firePropertyChange(String propertyName)
	{
		validatePropertyName(propertyName);
		PresentationModelPropertyChangeListeners propertyChangeListeners = propertyToItsChangeListeners.get(propertyName);
		if(propertyChangeListeners != null)
		{
			propertyChangeListeners.firePropertyChange();
		}
	}

	private void validatePropertyName(String propertyName)
	{
		Validate.notBlank(propertyName, "propertyName cannot be empty");
		Validate.isTrue(availablePropertyNames.contains(propertyName), "Bean '"+getBeanClassName()+"' does not contain given property'"+propertyName+"'");
	}
	
	private String getBeanClassName()
	{
		return bean.getClass().getName();
	}
	
	public void fireChangeAll()
	{
		for(PresentationModelPropertyChangeListeners propertyChangeListeners : propertyToItsChangeListeners.values())
		{
			propertyChangeListeners.firePropertyChange();
		}
	}
}
