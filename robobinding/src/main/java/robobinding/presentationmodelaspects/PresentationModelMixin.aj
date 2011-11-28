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
package robobinding.presentationmodelaspects;

import org.aspectj.lang.annotation.AdviceName;

import robobinding.property.ObservableProperties;
import robobinding.property.PropertyChangeListener;
import robobinding.property.PropertyChangeSupport;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robort Taylor
 * @author Cheng Wei
 */
public interface PresentationModelMixin extends ObservableProperties
{
	static aspect Impl
	{
		private PropertyChangeSupport PresentationModelMixin.propertyChangeSupport;
		
		public void PresentationModelMixin.addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			propertyChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void PresentationModelMixin.removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			propertyChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
		}
		
		//TODO: any potential method name conflicts with users'? may choose more specific name like refreshPresentationModel?
		public void PresentationModelMixin.refreshPresentationModel()
		{
			propertyChangeSupport.fireChangeAll();
		}
		
		private void PresentationModelMixin.firePropertyChange(String propertyName)
		{
			propertyChangeSupport.firePropertyChange(propertyName);
		}

		pointcut presentationModelCreation(PresentationModelMixin presentationModel) : initialization(
				PresentationModelMixin+.new(..)) && this(presentationModel);
		
		@AdviceName("initializePresentationModelChangeSupport")
		after(PresentationModelMixin presentationModel) returning : presentationModelCreation(presentationModel) 
		{
			presentationModel.propertyChangeSupport = new PropertyChangeSupport(presentationModel);
		}
	}

}
