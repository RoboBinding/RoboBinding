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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.function.Function;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModel;

/**
*
* @since 1.0
* @version $Revision: 1.0 $
* @author Robert Taylor
* @author Cheng Wei
*/
public abstract class ViewAttributeContractTest<T extends ViewAttribute>
{
	@Test (expected = AttributeBindingException.class)
	public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow()
	{
		T attribute = throwsExceptionDuringPreInitializingView();
		
		attribute.preInitializeView(bindingContextOfThrowingExceptionDuringPreInitializingView());
	}
	
	protected abstract T throwsExceptionDuringPreInitializingView();
	
	protected BindingContext bindingContextOfThrowingExceptionDuringPreInitializingView()
	{
		return newDefaultBindingContext();
	}
	
	@SuppressWarnings("unchecked")
	private BindingContext newDefaultBindingContext()
	{
		BindingContext bindingContext = mock(BindingContext.class);
		
		PresentationModelAdapter presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(bindingContext.getPresentationModelAdapter()).thenReturn(presentationModelAdapter);
	
		when(presentationModelAdapter.getPropertyValueModel(anyString())).thenReturn(mock(ValueModel.class));
		when(presentationModelAdapter.getReadOnlyPropertyValueModel(anyString())).thenReturn(mock(ValueModel.class));
		when(presentationModelAdapter.getDataSetPropertyValueModel(anyString())).thenReturn(mock(DataSetValueModel.class));
		when(presentationModelAdapter.findFunction(anyString(), (Class<?>)any())).thenReturn(mock(Function.class));
		
		return bindingContext;
	}

	@Test (expected = AttributeBindingException.class)
	public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow()
	{
		
		T attribute = throwsExceptionDuringBinding();
		
		attribute.bindTo(bindingContextOfThrowingExceptionDuringBinding());
	}
	
	
	protected abstract T throwsExceptionDuringBinding();
	
	
	protected BindingContext bindingContextOfThrowingExceptionDuringBinding()
	{
		return newDefaultBindingContext();
	}
}
