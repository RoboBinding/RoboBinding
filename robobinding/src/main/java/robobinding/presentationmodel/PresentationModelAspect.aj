/**
 * ViewAlbumPresentationModelAspect.aj
 * 22 Nov 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.presentationmodel;

import robobinding.NotifyPropertyChange;
import robobinding.internal.java_beans.Introspector;
import robobinding.itempresentationmodel.ItemPresentationModel;
import robobinding.property.ObservableProperties;
import robobinding.property.PropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public aspect PresentationModelAspect
{
	private static interface ObservablePropertiesMixin extends ObservableProperties
	{
		void setPresentationModelChangeSupport(PresentationModelChangeSupport presentationModelChangeSupport);

		PresentationModelChangeSupport getPresentationModelChangeSupport();

		static aspect Impl
		{
			private PresentationModelChangeSupport ObservablePropertiesMixin.presentationModelChangeSupport;

			public void ObservablePropertiesMixin.addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
			{
				presentationModelChangeSupport.addPropertyChangeListener(propertyName, propertyChangeListener);
			}

			public void ObservablePropertiesMixin.removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener)
			{
				presentationModelChangeSupport.removePropertyChangeListener(propertyName, propertyChangeListener);
			}

			public void ObservablePropertiesMixin.setPresentationModelChangeSupport(PresentationModelChangeSupport presentationModelChangeSupport)
			{
				this.presentationModelChangeSupport = presentationModelChangeSupport;
			}

			public PresentationModelChangeSupport ObservablePropertiesMixin.getPresentationModelChangeSupport()
			{
				return presentationModelChangeSupport;
			}
			
			public void ObservablePropertiesMixin.fireChangeAll()
			{
				presentationModelChangeSupport.fireChangeAll();
			}
		}
	}
	
	declare parents: @NotifyPropertyChange !ObservableProperties implements ObservablePropertiesMixin;

	pointcut beanCreation(ObservablePropertiesMixin presentationModel) : initialization(ObservablePropertiesMixin+.new(..)) && this(presentationModel);

	pointcut beanPropertyChange(ObservablePropertiesMixin presentationModel) : execution (void ObservablePropertiesMixin+.set*(*)) && this(presentationModel) && !within(ItemPresentationModel+);

	pointcut itemPresentationModelUpdate(ObservablePropertiesMixin presentationModel) : execution (void ItemPresentationModel+.setData(..)) && this(presentationModel) && within(ItemPresentationModel+);
	
	pointcut beanRefresh(ObservablePropertiesMixin presentationModel) : execution (@PresentationModelRefresh * ObservablePropertiesMixin+.*(..)) && this(presentationModel);
	
	after(ObservablePropertiesMixin presentationModel) returning : beanCreation(presentationModel) 
	{
		presentationModel.setPresentationModelChangeSupport(new PresentationModelChangeSupport(presentationModel));
	}

	after (ObservablePropertiesMixin presentationModel) : beanPropertyChange(presentationModel) 
	{
		String methodName = thisJoinPointStaticPart.getSignature().getName();
		String propertyName = Introspector.decapitalize(methodName.substring(3));
		presentationModel.getPresentationModelChangeSupport().firePropertyChange(propertyName);
	}
	
	after(ObservablePropertiesMixin presentationModel) : beanRefresh(presentationModel)
	{
		presentationModel.fireChangeAll();
	}
	
	after(ObservablePropertiesMixin presentationModel) : itemPresentationModelUpdate(presentationModel)
	{
		presentationModel.fireChangeAll();
	}
}
