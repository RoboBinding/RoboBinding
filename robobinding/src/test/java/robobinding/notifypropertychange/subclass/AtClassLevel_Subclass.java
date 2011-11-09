/**
 * AtClassLevel_Subclass.java
 * Nov 4, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.notifypropertychange.subclass;

import java.beans.PropertyChangeSupport;

import robobinding.NotifyPropertyChange;
import robobinding.notifypropertychange.AtClassLevel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@NotifyPropertyChange
public class AtClassLevel_Subclass extends AtClassLevel
{
	PropertyChangeSupport propertyChangeSupport;
	public AtClassLevel_Subclass()
	{
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	@Override
	public void setProperty1(boolean newValue)
	{
		boolean oldValue = getProperty1();
		super.setProperty1(newValue);
		propertyChangeSupport.firePropertyChange("property1", oldValue, newValue);
	}
	
	@Override
	public void setProperty2(String newValue)
	{
		String oldValue = getProperty2();
		super.setProperty2(newValue);
		propertyChangeSupport.firePropertyChange("property2", oldValue, newValue);
	}
}
