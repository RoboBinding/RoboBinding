/**
 * DefaultConstructorImplTest.java
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
package robobinding.property;

import org.junit.Assert;
import org.junit.Test;

import robobinding.itempresentationmodel.ItemPresentationModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DefaultConstructorImplTest
{
	@Test
	public void whenCreateFactoryUsingItemPresentationModelWithDefaultConstructor_thenSuccessful()
	{
		new DefaultConstructorImpl<Object>(ItemPresentationModelWithDefaultConstructor.class);
	}
	@Test
	public void givenItemPresentationModelFactory_whenNewItemPresentationModel_thenReturnNewInstance()
	{
		DefaultConstructorImpl<Object> factory = new DefaultConstructorImpl<Object>(ItemPresentationModelWithDefaultConstructor.class);
		
		ItemPresentationModel<Object> itemPresentationModel = factory.newItemPresentationModel();
		
		Assert.assertNotNull(itemPresentationModel);
	}
	@Test(expected=NullPointerException.class)
	public void whenCreateFactoryUsingItemPresentationModelWithoutDefaultConstructor_thenThrowException()
	{
		new DefaultConstructorImpl<Object>(ItemPresentationModelWithoutDefaultConstructor.class);
	}
	public static class ItemPresentationModelWithDefaultConstructor implements ItemPresentationModel<Object>
	{
		@Override
		public void setData(int index, Object bean)
		{
		}
	}
	public static class ItemPresentationModelWithoutDefaultConstructor implements ItemPresentationModel<Object>
	{
		public ItemPresentationModelWithoutDefaultConstructor(boolean parameter)
		{
		}
		@Override
		public void setData(int index, Object bean)
		{
		}
	}
}
