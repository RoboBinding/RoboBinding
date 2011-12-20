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
public abstract class AbstractTypeMappedTwoWayPropertyAttributeTest<IT, VST> extends AbstractTypeMappedPropertyAttributeTest<IT, VST>
{
	@Test
	public void whenBindingWith1WayOr2WayBinding_ThenViewShouldReflectValueModel()
	{
		createAttributeWithEither1WayOr2WayBinding();
		
		assertThat(getViewState(), equalTo(getInitialBindingExpectation().expectedViewState));
	}
	
	@Test
	public void givenViewBoundWithEither1WayOr2WayBinding_WhenUpdatingValueModel_ThenViewShouldBeUpdated()
	{
		createAttributeWith2WayBinding();
		//createAttributeWithEither1WayOr2WayBinding();
		
		for (BindingExpectation<IT, VST> bindingExpectation : bindingExpectations)
		{
			updateValueModel(bindingExpectation.inputValue);
			
			assertThat(getViewState(), equalTo(bindingExpectation.expectedViewState));
		}
	}
	
	@Test
	public void givenViewBoundWith1WayBinding_WhenUpdatingView_ThenValueModelShouldNotBeUpdated()
	{
		createAttributeWith1WayBinding();
		
		updateViewState(getLastBindingExpectation().expectedViewState);
		
		assertThat(valueModel.getValue(), equalTo(getInitialBindingExpectation().inputValue));
	}

	@Test
	public void givenViewBoundWith2WayBinding_WhenUpdatingView_ThenValueModelShouldBeUpdated()
	{
		createAttributeWith2WayBinding();
		
		updateViewState(getLastBindingExpectation().expectedViewState);
		
		assertThat(valueModel.getValue(), equalTo(getLastBindingExpectation().inputValue));
	}
	
	protected abstract void updateViewState(VST newValue);
	
}
