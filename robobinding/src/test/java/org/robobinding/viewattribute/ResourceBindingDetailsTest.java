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
package org.robobinding.viewattribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.content.res.Resources;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ResourceBindingDetailsTest
{
	private static final String RESOURCE_NAME = "resourceName";
	private static final String RESOURCE_TYPE = "resourceType";
	private static final String RESOURCE_PACKAGE = "resourcePackage";
	private Context context;
	private Resources resources;
	
	@Before
	public void setUp()
	{
		context = mock(Context.class);
		resources = mock(Resources.class);
		when(context.getResources()).thenReturn(resources);
		when(context.getPackageName()).thenReturn(RESOURCE_PACKAGE);
	}
	
	@Test
	public void givenResourceNameTypeAndPackage_thenGetResourceIdFromContextResources()
	{
		ResourceBindingDetails resourceBindingDetails = new ResourceBindingDetails(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE);
		int resourceId = anyNumber();
		when(resources.getIdentifier(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE)).thenReturn(resourceId);
		
		int result = resourceBindingDetails.getResourceId(context);
		
		assertThat(result, equalTo(resourceId));
	}

	@Test
	public void givenOnlyResourceNameAndType_thenUseContextPackageToGetResourceId()
	{
		ResourceBindingDetails resourceBindingDetails = new ResourceBindingDetails(RESOURCE_NAME, RESOURCE_TYPE);
		int resourceId = anyNumber();
		when(resources.getIdentifier(RESOURCE_NAME, RESOURCE_TYPE, RESOURCE_PACKAGE)).thenReturn(resourceId);
		
		int result = resourceBindingDetails.getResourceId(context);
		
		assertThat(result, equalTo(resourceId));
	}
	
	private int anyNumber()
	{
		return new Random().nextInt(1000);
	}
}
