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
package robobinding.itempresentationmodelaspects;

import robobinding.presentationmodel.PresentationModelChangeSupport;
import robobinding.presentationmodelaspects.ObservablePresentationModel;
import robobinding.property.PropertyChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ImplementsObservablePresentationModel extends AbstractItemPresentationModel implements ObservablePresentationModel
{
	private PresentationModelChangeSupport presentationModelChangeSupport;
	public ImplementsObservablePresentationModel()
	{
		presentationModelChangeSupport = new PresentationModelChangeSupport(this);
	}
	@Override
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		presentationModelChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		presentationModelChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public PresentationModelChangeSupport getPresentationModelChangeSupport()
	{
		return presentationModelChangeSupport;
	}
}
