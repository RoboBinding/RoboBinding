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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractTypeMappedOneWayPropertyAttributeTest<IT, VST> extends AbstractTypeMappedPropertyAttributeTest<IT, VST>
{
	@Test
	public void whenBinding_ThenViewShouldReflectModel()
	{
		createAttributeWith1WayBinding();
		
		assertThat(getViewState(), equalTo(getInitialBindingExpectation().expectedViewState));
	}
	
	@Test
	public void givenBound_WhenValueModelIsUpdated_ThenViewShouldReflectChanges()
	{
		createAttributeWith1WayBinding();
		
		for (BindingExpectation<IT, VST> bindingExpectation : bindingExpectations)
		{	
			updateValueModel(bindingExpectation.inputValue);
			
			assertThat(getViewState(), equalTo(bindingExpectation.expectedViewState));
		}
	}
	
	@Test(expected=RuntimeException.class)
	public void whenAttempting2WayBinding_ThenThrowARuntimeException()
	{
		createAttributeWith2WayBinding();
	}
	
}
