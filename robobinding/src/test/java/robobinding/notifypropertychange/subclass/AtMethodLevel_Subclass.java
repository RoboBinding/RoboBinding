/**
 * AtMethodLevel_Subclass.java
 * Nov 2, 2011 Copyright Cheng Wei and Robert Taylor
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

import robobinding.NotifyPropertyChange;
import robobinding.notifypropertychange.AtMethodLevel;
import robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AtMethodLevel_Subclass extends AtMethodLevel
{
	private PresentationModelChangeSupport presentationModelChangeSupport;
	public AtMethodLevel_Subclass(boolean property1, boolean property2)
	{
		super(property1, property2);
		
		presentationModelChangeSupport = new PresentationModelChangeSupport(this);
	}
	@Override
	@NotifyPropertyChange
	public void setProperty1(boolean newValue)
	{
		super.setProperty1(newValue);
		presentationModelChangeSupport.firePropertyChange("property1");
	}
}
