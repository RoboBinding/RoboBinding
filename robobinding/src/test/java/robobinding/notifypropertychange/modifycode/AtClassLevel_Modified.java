/**
 * AtClassLevel_Modified.java
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
package robobinding.notifypropertychange.modifycode;

import robobinding.CustomSetter;
import robobinding.NotifyPropertyChange;
import robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@NotifyPropertyChange
public class AtClassLevel_Modified
{
	private boolean property1;
	private String property2;
	private boolean customProperty;
	
	private PresentationModelChangeSupport presentationModelChangeSupport;
	public AtClassLevel_Modified()
	{
		presentationModelChangeSupport = new PresentationModelChangeSupport(this);
	}
	
	public boolean getProperty1()
	{
		return property1;
	}
	public void setProperty1(boolean newValue)
	{
		this.property1 = newValue;
		presentationModelChangeSupport.firePropertyChange("property1");
	}
	
	public String getProperty2()
	{
		return property2;
	}
	public void setProperty2(String newValue)
	{
		this.property2 = newValue;
		presentationModelChangeSupport.firePropertyChange("property2");
	}
	
	public void property2SetterInvoker()
	{
		setProperty2("something new");
	}
	
	public boolean getCustomProperty()
	{
		return customProperty;
	}
	@CustomSetter
	public void setCustomProperty(boolean newValue)
	{
		customProperty = newValue;
		presentationModelChangeSupport.firePropertyChange("customProperty");
	}
	
	public void setPropertyWithoutGetter(boolean b)
	{
		
	}
	
	public boolean getPropertyWithoutParameter()
	{
		return false;
	}
	public void setPropertyWithoutParameter()
	{
	}
	
	public void setMalformedProperty(boolean b1, boolean b2)
	{
	}
}
