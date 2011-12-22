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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
		propertyCreator = new PropertyCreator(new Bean());
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateNonExistingProperty_thenThrowException()
	{
		propertyCreator.createProperty("nonExistingProperty");
	}
	@Test
	public void whenCreateListDataSetProperty_thenReturnInstance()
	{
		DataSetProperty<Object> listDataSetProperty = propertyCreator.createDataSetProperty(Bean.LIST_DATA_SET_PROPERTY);
		
		Assert.assertNotNull(listDataSetProperty);
	}
	@Test
	public void whenCreateArrayDataSetProperty_thenReturnInstance()
	{
		DataSetProperty<Object> arrayDataSetProperty = propertyCreator.createDataSetProperty(Bean.ARRAY_DATA_SET_PROPERTY);
		
		Assert.assertNotNull(arrayDataSetProperty);
	}
	@Test
	public void whenCreateCursorDataSetProperty_thenReturnInstance()
	{
		DataSetProperty<Object> cursorDataSetProperty = propertyCreator.createDataSetProperty(Bean.CURSOR_DATA_SET_PROPERTY);
		
		Assert.assertNotNull(cursorDataSetProperty);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateUnsupportedDataSetProperty_thenThrowException()
	{
		propertyCreator.createDataSetProperty(Bean.UNSUPPORTED_DATA_SET_PROPERTY);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateDataSetPropertyWithoutItemPresentationModelAnnotation_thenThrowException()
	{
		propertyCreator.createDataSetProperty(Bean.DATA_SET_PROPERTY_WITHOUT_ITEM_PRESENTATION_MODEL_ANNOTATION);
	}
}
