/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.BindingAttributeValues;
import org.robobinding.viewattribute.adapterview.ItemLayoutAttribute.DynamicLayoutAttribute;
import org.robobinding.viewattribute.adapterview.ItemLayoutAttribute.StaticLayoutAttribute;

import android.widget.AdapterView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ItemLayoutAttributeTest
{
	private AdapterView<?> adapterView;
	
	@Before
	public void setUp()
	{
		adapterView = mock(AdapterView.class);
	}
	
	@Test
	public void whenCreatingWithAStaticLayoutValue_thenInitializeStaticLayoutAttribute()
	{
		String staticLayoutAttribute = "@layout/some_resource";
		
		ItemLayoutAttribute itemLayoutAttribute = new ItemLayoutAttribute(adapterView, staticLayoutAttribute);
	
		assertThat(itemLayoutAttribute.layoutAttribute, instanceOf(StaticLayoutAttribute.class));
	}
	
	@Test
	public void whenCreatingWithAPropertyValue_thenInitializeDynamicLayoutAttribute()
	{
		String dynamicLayoutAttribute = BindingAttributeValues.ONE_WAY_BINDING_DEFAULT_PROPERTY_NAME;
		
		ItemLayoutAttribute itemLayoutAttribute = new ItemLayoutAttribute(adapterView, dynamicLayoutAttribute);
	
		assertThat(itemLayoutAttribute.layoutAttribute, instanceOf(DynamicLayoutAttribute.class));
		assertDynamicLayoutAttributeWasInitializedCorrectly((DynamicLayoutAttribute)itemLayoutAttribute.layoutAttribute);
	}

	private void assertDynamicLayoutAttributeWasInitializedCorrectly(DynamicLayoutAttribute layoutAttribute)
	{
		DynamicLayoutAttributeUtils.bindAttribute(mock(DataSetAdapter.class), layoutAttribute);
	}
	
}
