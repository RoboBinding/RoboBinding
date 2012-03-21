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
package org.robobinding.viewattribute.adapterview;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.MockBindingContext;
import org.robobinding.PredefinedPendingAttributesForView;

import android.content.Context;
import android.content.res.Resources;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ItemMappingAttributeTest
{
	protected static final String MAPPING_ATTRIBUTE_VALUE = "[text1.text:{property}]";
	protected Context mockContext;
	protected DataSetAdapter<?> dataSetAdapter;
	
	@Before
	public void setUp()
	{
		Resources mockResources = mock(Resources.class);
		when(mockResources.getIdentifier("text1", "id", "android")).thenReturn(1);
		mockContext = mock(Context.class);
		when(mockContext.getResources()).thenReturn(mockResources);
		dataSetAdapter = mock(DataSetAdapter.class);
	}
	
	@Test
	public void whenBinding_thenUpdateDataSetAdapter()
	{
		ItemMappingAttribute itemMappingAttribute = new ItemMappingAttribute(MAPPING_ATTRIBUTE_VALUE);
		
		itemMappingAttribute.bind(dataSetAdapter, MockBindingContext.create(mockContext));
		
		verify(dataSetAdapter).setItemPredefinedPendingAttributesForViewGroup(anyCollectionOf(PredefinedPendingAttributesForView.class));
	}
	
	@Test
	public void whenBinding_thenInitializeViewMappings()
	{
		ItemMappingAttribute itemMappingAttribute = new ItemMappingAttribute(MAPPING_ATTRIBUTE_VALUE);
		
		itemMappingAttribute.bind(dataSetAdapter, MockBindingContext.create(mockContext));
		
		assertNotNull(itemMappingAttribute.getViewMappingsCollection());
	}
}
