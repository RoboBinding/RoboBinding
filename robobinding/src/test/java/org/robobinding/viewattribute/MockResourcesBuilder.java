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
package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.content.Context;
import android.content.res.Resources;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockResourcesBuilder
{
	private Context mockContext;
	private Resources mockResources;
	
	public MockResourcesBuilder()
	{
		mockContext = mock(Context.class);
		when(mockContext.getPackageName()).thenReturn(BindingAttributeValues.DEFAULT_RESOURCE_PACKAGE);
		
		mockResources = mock(Resources.class);
		when(mockContext.getResources()).thenReturn(mockResources);
	}
	
	public int desclareLayoutResource(String resourceName)
	{
		int layoutId = nextResourceId();
		when(mockResources.getIdentifier(
				resourceName, 
				BindingAttributeValues.LAYOUT_RESOURCE_TYPE, 
				BindingAttributeValues.DEFAULT_RESOURCE_PACKAGE))
				.thenReturn(layoutId);
		return layoutId;
	}
	
	private int resourceIdCounter = 1;
	private int nextResourceId()
	{
		return resourceIdCounter++;
	}
	
	public Context build()
	{
		return mockContext;
	}
}
