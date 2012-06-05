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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.attribute.MockResourcesBuilder.aContextOfResources;

import org.junit.Test;
import org.robobinding.MockBindingContext;
import org.robobinding.attribute.MockResourcesBuilder;
import org.robobinding.presentationmodel.MockPresentationModelAdapterBuilder;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.BindingAttributeValues;

import android.content.Context;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewCreatorTest
{
	@Test
	public void whenGetLayoutId_returnExpectedResult()
	{
		MockResourcesBuilder aContextOfResources = aContextOfResources();
		int layoutId = aContextOfResources.desclareLayoutResource(BindingAttributeValues.DEFAULT_LAYOUT_RESOURCE_NAME);
		
		SubViewCreator subViewCreator = new SubViewCreator(MockBindingContext.create(aContextOfResources.build()), BindingAttributeValues.DEFAULT_LAYOUT_RESOURCE);
		
		assertThat(subViewCreator.getLayoutId(), equalTo(layoutId));
	}
	
	@Test
	public void whenGetPresentationModel_returnExpectedResult()
	{
		Object presentationModel = new Object();
		String presentationModelAttributeValue = BindingAttributeValues.ONE_WAY_BINDING_DEFAULT_PROPERTY_NAME; 
		PresentationModelAdapter presentationModelAdapter = MockPresentationModelAdapterBuilder.createWithReadOnlyDefaultProperty(presentationModel);
		
		SubViewCreator subViewCreator = new SubViewCreator(MockBindingContext.create(presentationModelAdapter), null);
	
		assertThat(subViewCreator.getPresentationModel(presentationModelAttributeValue),
				equalTo(presentationModel));
	}
}
