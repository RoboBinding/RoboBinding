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
package org.robobinding.property;

import java.util.List;

import org.robobinding.DependsOnStateOf;
import org.robobinding.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.property.ObservableProperties;
import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.PresentationModelPropertyChangeListeners;
import org.robobinding.property.PresentationModelPropertyChangeSupport;


/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ObservableTestBean extends TestBean implements ObservableProperties
{
	public static final String LIST_DATA_SET_PROPERTY = "listDataSetProperty";
	public static final String ARRAY_DATA_SET_PROPERTY = "arrayDataSetProperty";
	public static final String CURSOR_DATA_SET_PROPERTY = "cursorDataSetProperty";
	public static final String UNSUPPORTED_DATA_SET_PROPERTY = "UnsupportedDataSetProperty";
	public static final String DATA_SET_PROPERTY_WITHOUT_ITEM_PRESENTATION_MODEL_ANNOTATION = "DataSetPropertyWithoutItemPresentationModelAnnotation";
	
	public static final String PROPERTY_WITH_VALID_DEPENDENT_PROPERTIES = "propertyWithValidDependentProperties";
	public static final String DEPENDENT_PROPERTY = "dependentProperty";
	public static final String PROPERTY_WITH_DUPLICATED_DEPENDENT_PROPERTIES = "propertyWithDuplicatedDependentProperties";
	public static final String PROPERTY_WITH_SOME_NONEXISTING_DEPENDENT_PROPERTIES = "propertyWithSomeNonExistingDependentProperties";
	public static final String PROPERTY_DEPENDING_ON_SELF = "propertyDependingOnSelf";
	
	private PresentationModelPropertyChangeSupport propertyChangeSupport;
	public ObservableTestBean()
	{
		propertyChangeSupport = new PresentationModelPropertyChangeSupport(this);
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public List<Object> getListDataSetProperty()
	{
		return null;
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public Object[] getArrayDataSetProperty()
	{
		return null;
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public TypedCursor<Object> getCursorDataSetProperty()
	{
		return null;
	}
	@ItemPresentationModel(ItemPresentationModelImpl.class)
	public Object[] getUnsupportedDataSetProperty()
	{
		return null;
	}
	public List<Object> getDataSetPropertyWithoutItemPresentationModelAnnotation()
	{
		return null;
	}
	@DependsOnStateOf({DEPENDENT_PROPERTY,PROPERTY})
	public boolean getPropertyWithValidDependentProperties()
	{
		return true;
	}
	public boolean getDependentProperty()
	{
		return true;
	}
	@DependsOnStateOf({DEPENDENT_PROPERTY,DEPENDENT_PROPERTY})
	public boolean getPropertyWithDuplicatedDependentProperties()
	{
		return true;
	}
	@DependsOnStateOf({PROPERTY,"nonExistingProperty1"})
	public boolean getPropertyWithSomeNonExistingDependentProperties()
	{
		return true;
	}
	@DependsOnStateOf(PROPERTY_DEPENDING_ON_SELF)
	public boolean getPropertyDependingOnSelf()
	{
		return true;
	}
	@Override
	public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	@Override
	public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
	public boolean hasPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
	{
		PresentationModelPropertyChangeListeners propertyChangeListeners = propertyChangeSupport.getPropertyChangeListeners(propertyName);
		return propertyChangeListeners.contains(listener);
	}
	public boolean isPropertyChangeListenerRegisteredNumTimes(String propertyName, PresentationModelPropertyChangeListener listener, int expectedNumTimes)
	{
		PresentationModelPropertyChangeListeners propertyChangeListeners = propertyChangeSupport.getPropertyChangeListeners(propertyName);
		
		int actualNumTimes = 0;
		for(PresentationModelPropertyChangeListener propertyChangeListener : propertyChangeListeners)
		{
			if(propertyChangeListener == listener)
			{
				actualNumTimes++;
			}
		}
		return actualNumTimes == expectedNumTimes;
	}
}