/**
 * AtClassLevel_Wrapper.java
 * Nov 7, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.notifypropertychange.wrapper;

import robobinding.CustomSetter;
import robobinding.NotifyPropertyChange;
import robobinding.notifypropertychange.AtClassLevel;
import robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@NotifyPropertyChange
public class AtClassLevel_Wrapper
{
	private AtClassLevel atClassLevel;
	
	private PresentationModelChangeSupport presentationModelChangeSupport;
	public AtClassLevel_Wrapper(AtClassLevel atClassLevel)
	{
		this.atClassLevel = atClassLevel;
		
		presentationModelChangeSupport = new PresentationModelChangeSupport(this);
	}
	
	public boolean getProperty1()
	{
		return atClassLevel.getProperty1();
	}
	public void setProperty1(boolean newValue)
	{
		atClassLevel.setProperty1(newValue);
		presentationModelChangeSupport.firePropertyChange("property1");
	}
	
	public String getProperty2()
	{
		return atClassLevel.getProperty2();
	}
	public void setProperty2(String newValue)
	{
		atClassLevel.setProperty2(newValue);
		presentationModelChangeSupport.firePropertyChange("property2");
	}
	
	public void property2SetterInvoker()
	{
		//property2 property change notification can not be achieved.
		atClassLevel.property2SetterInvoker();
	}
	
	public boolean getCustomProperty()
	{
		return atClassLevel.getCustomProperty();
	}
	@CustomSetter
	public void setCustomProperty(boolean newValue)
	{
		atClassLevel.setCustomProperty(newValue);
	}
	
	public void setPropertyWithoutGetter(boolean b)
	{
		atClassLevel.setPropertyWithoutGetter(b);
	}
	
	public boolean getPropertyWithoutParameter()
	{
		return atClassLevel.getPropertyWithoutParameter();
	}
	public void setPropertyWithoutParameter()
	{
		atClassLevel.setPropertyWithoutParameter();
	}
	
	public void setMalformedProperty(boolean b1, boolean b2)
	{
		atClassLevel.setMalformedProperty(b1, b2);
	}
}
