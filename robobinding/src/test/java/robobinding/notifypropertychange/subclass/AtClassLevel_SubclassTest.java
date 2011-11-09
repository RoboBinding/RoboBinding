/**
 * AtClassLevel_SubclassTest.java
 * Nov 9, 2011 Copyright Cheng Wei and Robert Taylor
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AtClassLevel_SubclassTest
{
	@Test
	public void whenCallProperty2SetterInvoker_thenProperty2ChangeNotified()
	{
		AtClassLevel_Subclass atClassLevel_Subclass = new AtClassLevel_Subclass();
		PropertyChangeListener mockPropertyChangeListener = EasyMock.createMock(PropertyChangeListener.class);
		mockPropertyChangeListener.propertyChange((PropertyChangeEvent)EasyMock.anyObject());
		EasyMock.replay(mockPropertyChangeListener);
		atClassLevel_Subclass.propertyChangeSupport.addPropertyChangeListener("property2", mockPropertyChangeListener);
		 
		atClassLevel_Subclass.property2SetterInvoker();
		
		EasyMock.verify(mockPropertyChangeListener);
	}
}
