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

import robobinding.presentationmodel.PresentationModelChangeSupport;
import robobinding.property.PropertyChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ObservablePresentationModelMixin extends ObservablePresentationModel
{
	static aspect Impl
	{
		private PresentationModelChangeSupport ObservablePresentationModelMixin.presentationModelChangeSupport;
		
		public void ObservablePresentationModelMixin.addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			presentationModelChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
		}

		public void ObservablePresentationModelMixin.removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
		{
			presentationModelChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
		}
		
		public PresentationModelChangeSupport ObservablePresentationModelMixin.getPresentationModelChangeSupport()
		{
			return presentationModelChangeSupport;
		}
		
		pointcut presentationModelCreation(ObservablePresentationModelMixin presentationModel) : initialization(
				ObservablePresentationModelMixin+.new(..)) && this(presentationModel);
		
		@AdviceName("initializePresentationModelChangeSupport")
		after(ObservablePresentationModelMixin presentationModel) returning : presentationModelCreation(presentationModel) 
		{
			presentationModel.presentationModelChangeSupport = new PresentationModelChangeSupport(presentationModel);
		}
	}

}
