/**
 * AtClassLevel_Gen.java
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
package robobinding.notifypropertychange;

import java.beans.PropertyChangeSupport;

import robobinding.NotifyPropertyChange;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@NotifyPropertyChange
public class AtClassLevel_Gen
{
	private boolean property1;
	private String property2;
	
	private PropertyChangeSupport propertyChangeSupport;
	public AtClassLevel_Gen()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	public boolean getProperty1()
	{
		return property1;
	}
	public void setProperty1(boolean b)
	{
		boolean oldValue = getProperty1();
		this.property1 = b;
		propertyChangeSupport.firePropertyChange("property1", oldValue, b);
	}
	public String getProperty2()
	{
		return property2;
	}
	public void setProperty2(String str)
	{
		String oldValue = getProperty2();
		this.property2 = str;
		propertyChangeSupport.firePropertyChange("property2", oldValue, str);
	}
	
	public void setPropertyWithGetter(boolean b)
	{
		
	}
	public void setPropertyWithoutParameter()
	{
	}
	
	public void setMalformedProperty(boolean b1, boolean b2)
	{
	}
}
