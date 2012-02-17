/**
 * FactoryMethodImplTest.java
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
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FactoryMethodImplTest
{
	private ObjectUnderTest objectUnderTest;
	@Before
	public void setUp()
	{
		objectUnderTest = new ObjectUnderTest();
	}
	@Test
	public void whenCreateUsingFactoryMethodWithValidReturnType_thenSuccessful()
	{
		new FactoryMethodImpl<Object>(
				objectUnderTest, 
				ItemPresentationModelImpl.class, 
				ObjectUnderTest.FACTORY_METHOD_WITH_VALID_RETURN_TYPE);
	}
	@Test
	public void givenItemPresentationModelFactory_whenNewPresentationModel_returnNewInstance()
	{
		ItemPresentationModelFactory<Object> factory = new FactoryMethodImpl<Object>(
				objectUnderTest, 
				ItemPresentationModelImpl.class, 
				ObjectUnderTest.FACTORY_METHOD_WITH_VALID_RETURN_TYPE);
		
		ItemPresentationModel<Object> itemPresentationModel = factory.newItemPresentationModel();
		
		Assert.assertNotNull(itemPresentationModel);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateUsingFactoryMethodWithInvalidReturnType_thenThrowException()
	{
		new FactoryMethodImpl<Object>(
				objectUnderTest, 
				ItemPresentationModelImpl.class, 
				ObjectUnderTest.FACTORY_METHOD_WITH_INVALID_RETURN_TYPE);
	}
	@Test(expected=RuntimeException.class)
	public void whenCreateUsingNonExistingFactoryMethod_thenThrowException()
	{
		new FactoryMethodImpl<Object>(
				objectUnderTest, 
				ItemPresentationModelImpl.class, 
				"nonExistingFactoryMethod");
	}
	public static class ObjectUnderTest
	{
		public static final String FACTORY_METHOD_WITH_VALID_RETURN_TYPE = "factoryMethodWithValidReturnType";
		public static final String FACTORY_METHOD_WITH_INVALID_RETURN_TYPE = "factoryMethodWithInvalidReturnType";
		
		public ItemPresentationModelImpl factoryMethodWithValidReturnType()
		{
			return new ItemPresentationModelImpl();
		}
		public Object factoryMethodWithInvalidReturnType()
		{
			return new Object();
		}
	}
}
