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
package org.robobinding.binding.viewattribute;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.binding.viewattribute.DropdownMappingAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DropdownMappingAttributeTest extends ItemMappingAttributeTest
{
	@Test
	public void whenBinding_ThenUpdateDataSetAdapter()
	{
		DropdownMappingAttribute dropdownMappingAttribute = new DropdownMappingAttribute(MAPPING_ATTRIBUTE_VALUE, false);
		
		dropdownMappingAttribute.bind(dataSetAdapter, null, mockContext);
		
		verify(dataSetAdapter).setDropdownMappingAttribute(dropdownMappingAttribute);
	}
	
	@Test
	public void whenBinding_ThenInitializeViewMappings()
	{
		DropdownMappingAttribute dropdownMappingAttribute = new DropdownMappingAttribute(MAPPING_ATTRIBUTE_VALUE, false);
		
		dropdownMappingAttribute.bind(dataSetAdapter, null, mockContext);
		
		assertNotNull(dropdownMappingAttribute.getViewMappingsCollection());
	}
}
