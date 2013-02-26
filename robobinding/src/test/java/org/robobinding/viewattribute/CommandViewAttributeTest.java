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

import org.junit.Before;
import org.junit.Test;
import org.robobinding.MockBindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.function.Function;
import org.robobinding.function.MockFunction;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.adapterview.ItemClickEvent;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public final class CommandViewAttributeTest
{
	private static final String FUNCTION_NAME = "functionName";
	
	private Context context = null;
	private Function noArgsFunction = new MockFunction();
	private Function preferredFunction = new MockFunction();
	
	private DummyCommandViewAttribute commandViewAttribute;
	private PresentationModelAdapter presentationModelAdapter;
	
	@Before
	public void setUp()
	{
		commandViewAttribute = new DummyCommandViewAttribute();
		commandViewAttribute.setView(mock(View.class));
		commandViewAttribute.setAttribute(new CommandAttribute("name", FUNCTION_NAME));
		presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@Test
	public void givenAPresentationModelWithAMatchingNoArgsFunction_whenBinding_thenBindWithThatFunction()
	{
		when(presentationModelAdapter.findFunction(FUNCTION_NAME)).thenReturn(noArgsFunction);
		
		bindAttribute();

		assertThat(commandViewAttribute.functionBound, equalTo(noArgsFunction));
	}

	private void bindAttribute()
	{
		commandViewAttribute.bindTo(MockBindingContext.create(presentationModelAdapter, context));
	}
	
	@Test
	public void givenAPresentationModelWithAMatchingPreferredArgsFunction_whenBinding_thenBindWithThatFunctionAndParamsBuilder()
	{
		when(presentationModelAdapter.findFunction(FUNCTION_NAME)).thenReturn(noArgsFunction);
		when(presentationModelAdapter.findFunction(FUNCTION_NAME, ItemClickEvent.class)).thenReturn(preferredFunction);
		
		bindAttribute();
		
		assertThat(commandViewAttribute.functionBound, equalTo(preferredFunction));
	}
	
	@Test (expected=RuntimeException.class)
	public void givenAPresentationModelWithNoMatchingFunction_whenBinding_thenThrowRuntimeException()
	{
		bindAttribute();
	}
	
	@Test (expected=IllegalStateException.class)
	public void givenAnAttributeWhosePropertiesHaveNotBeenSet_whenBinding_thenThrowException()
	{
		commandViewAttribute = new DummyCommandViewAttribute();
		
		bindAttribute();
	}
	
	public class DummyCommandViewAttribute extends AbstractCommandViewAttribute<View>
	{
		Function functionBound;
		
		@Override
		protected void bind(Command parameterObject)
		{
			this.functionBound = parameterObject.function;
		}

		@Override
		protected Class<?> getPreferredCommandParameterType()
		{
			return ItemClickEvent.class;
		}
		
	}
}
