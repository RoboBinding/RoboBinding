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
import static org.robobinding.viewattribute.MockCommandViewAttributeConfigBuilder.aCommandViewAttributeConfig;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.function.Function;
import org.robobinding.viewattribute.adapterview.ItemClickEvent;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public final class CommandViewAttributeTest extends ViewAttributeContractTest<AbstractCommandViewAttribute<View>> {
    private static final String FUNCTION_NAME = "functionName";

    @Mock
    Function noArgsFunction;
    @Mock
    Function preferredFunction;
    @Mock
    BindingContext bindingContext;
    private DummyCommandViewAttribute commandViewAttribute;

    @Before
    public void setUp() {
	commandViewAttribute = new DummyCommandViewAttribute();
	commandViewAttribute.initialize(aCommandViewAttributeConfig(mock(View.class), FUNCTION_NAME));
    }

    @Test
    public void givenAPresentationModelWithAMatchingNoArgsFunction_whenBinding_thenBindWithThatFunction() {
	when(bindingContext.findFunction(FUNCTION_NAME)).thenReturn(noArgsFunction);

	commandViewAttribute.bindTo(bindingContext);

	assertThat(commandViewAttribute.functionBound, equalTo(noArgsFunction));
    }

    @Test
    public void givenAPresentationModelWithAMatchingPreferredArgsFunction_whenBinding_thenBindWithThatFunctionAndParamsBuilder() {
	when(bindingContext.findFunction(FUNCTION_NAME)).thenReturn(noArgsFunction);
	when(bindingContext.findFunction(FUNCTION_NAME, ItemClickEvent.class)).thenReturn(preferredFunction);

	commandViewAttribute.bindTo(bindingContext);

	assertThat(commandViewAttribute.functionBound, equalTo(preferredFunction));
    }

    @Test(expected = RuntimeException.class)
    public void givenAPresentationModelWithNoMatchingFunction_whenBinding_thenThrowRuntimeException() {
	commandViewAttribute.bindTo(bindingContext);
    }

    public class DummyCommandViewAttribute extends AbstractCommandViewAttribute<View> {
	Function functionBound;

	@Override
	protected void bind(Command parameterObject) {
	    this.functionBound = parameterObject.function;
	}

	@Override
	protected Class<?> getPreferredCommandParameterType() {
	    return ItemClickEvent.class;
	}

    }

    @Test
    @Ignore
    @Override
    public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow() {
    }

    @Override
    protected AbstractCommandViewAttribute<View> throwsExceptionDuringPreInitializingView() {
	throw new UnsupportedOperationException();
    }

    @Override
    protected AbstractCommandViewAttribute<View> throwsExceptionDuringBinding() {
	return new ThrowsExceptionDuringBinding();
    }

    private static class ThrowsExceptionDuringBinding extends AbstractCommandViewAttribute<View> {
	public ThrowsExceptionDuringBinding() {
	    initialize(aCommandViewAttributeConfig(mock(View.class), FUNCTION_NAME));
	}

	@Override
	protected void bind(Command command) {
	    throw new RuntimeException();
	}

	@Override
	protected Class<?> getPreferredCommandParameterType() {
	    return Integer.class;
	}
    }
}
