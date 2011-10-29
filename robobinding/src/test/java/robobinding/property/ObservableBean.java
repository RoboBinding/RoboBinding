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
package robobinding.property;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import robobinding.DependsOn;
import robobinding.presentationmodel.ObservableProperties;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ObservableBean extends Bean implements ObservableProperties
{
	public static final String PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES = "propertyWithValidDependentProperties";
	public static final String PROPERTY_WITH_DUPLICATED_DEPENDENT_PROPERTIES = "propertyWithDuplicatedDependentProperties";
	public static final String PROPERTY_WITH_SOME_NONEXISTING_DEPENDENT_PROPERTIES = "propertyWithSomeNonExistingDependentProperties";
	public static final String PROPERTY_DEPENDING_ON_SELF = "propertyDependingOnSelf";
	
	private PropertyChangeSupport propertyChangeSupport;
	public ObservableBean()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	@DependsOn({ANNOTATED_PROPERTY,PROPERTY})
	public boolean getPropertyWithValidDependentProperties()
	{
		return true;
	}
	@DependsOn({PROPERTY,PROPERTY})
	public boolean getPropertyWithDuplicatedDependentProperties()
	{
		return true;
	}
	@DependsOn({PROPERTY,"nonExistingProperty1"})
	public boolean getPropertyWithSomeNonExistingDependentProperties()
	{
		return true;
	}
	@DependsOn(PROPERTY_DEPENDING_ON_SELF)
	public boolean getPropertyDependingOnSelf()
	{
		return true;
	}
	@Override
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	@Override
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
	public boolean hasPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		List<PropertyChangeListener> propertyListeners = Lists.newArrayList(propertyChangeSupport.getPropertyChangeListeners(propertyName));
		return propertyListeners.contains(listener);
	}
	public boolean isPropertyChangeListenerRegisteredNumTimes(String propertyName, PropertyChangeListener listener, int expectedNumTimes)
	{
		List<PropertyChangeListener> propertyChangeListeners = Lists.newArrayList(propertyChangeSupport.getPropertyChangeListeners(propertyName));
		
		int actualNumTimes = 0;
		for(PropertyChangeListener propertyChangeListener : propertyChangeListeners)
		{
			if(propertyChangeListener == listener)
			{
				actualNumTimes++;
			}
		}
		return actualNumTimes == expectedNumTimes;
	}
}