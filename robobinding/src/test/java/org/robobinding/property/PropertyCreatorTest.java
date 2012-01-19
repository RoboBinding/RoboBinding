/**
 * PropertyCreatorTest.java
 * Oct 29, 2011 Copyright Cheng Wei and Robert Taylor
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyCreatorTest
{
	private PropertyCreator propertyCreator;
	@Before
	public void setUp()
	{
		propertyCreator = new PropertyCreator(new ObservableTestBean());
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateNonExistingProperty_thenThrowException()
	{
		propertyCreator.createProperty("nonExistingProperty");
	}
	@Test
	public void whenCreateListDataSetProperty_thenReturnInstance()
	{
		DataSetPropertyValueModel<Object> listDataSetProperty = propertyCreator.createDataSetProperty(ObservableTestBean.LIST_DATA_SET_PROPERTY);
		
		Assert.assertNotNull(listDataSetProperty);
	}
	@Test
	public void whenCreateArrayDataSetProperty_thenReturnInstance()
	{
		DataSetPropertyValueModel<Object> arrayDataSetProperty = propertyCreator.createDataSetProperty(ObservableTestBean.ARRAY_DATA_SET_PROPERTY);
		
		Assert.assertNotNull(arrayDataSetProperty);
	}
	@Test
	public void whenCreateCursorDataSetProperty_thenReturnInstance()
	{
		DataSetPropertyValueModel<Object> cursorDataSetProperty = propertyCreator.createDataSetProperty(ObservableTestBean.CURSOR_DATA_SET_PROPERTY);
		
		Assert.assertNotNull(cursorDataSetProperty);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateUnsupportedDataSetProperty_thenThrowException()
	{
		propertyCreator.createDataSetProperty(ObservableTestBean.UNSUPPORTED_DATA_SET_PROPERTY);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateDataSetPropertyWithoutItemPresentationModelAnnotation_thenThrowException()
	{
		propertyCreator.createDataSetProperty(ObservableTestBean.DATA_SET_PROPERTY_WITHOUT_ITEM_PRESENTATION_MODEL_ANNOTATION);
	}
	
	static class ObservableTestBean implements ObservableProperties
	{
		public static final String LIST_DATA_SET_PROPERTY = "listDataSetProperty";
		public static final String ARRAY_DATA_SET_PROPERTY = "arrayDataSetProperty";
		public static final String CURSOR_DATA_SET_PROPERTY = "cursorDataSetProperty";
		public static final String UNSUPPORTED_DATA_SET_PROPERTY = "UnsupportedDataSetProperty";
		public static final String DATA_SET_PROPERTY_WITHOUT_ITEM_PRESENTATION_MODEL_ANNOTATION = "DataSetPropertyWithoutItemPresentationModelAnnotation";
		
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
		@Override
		public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
		{
		}
		@Override
		public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener)
		{
		}
	}
}
