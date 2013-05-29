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
package org.robobinding.attribute;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robobinding.attribute.Attributes.aCommandAttribute;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robobinding.BindingContext;
import org.robobinding.function.Function;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class CommandAttributeTest {
    @DataPoints
    public static String[] illegalAttributeValues = {"{invalid_command_name}", "{invalid_command_name", "invalid_command_name}"};

    private static final String COMMAND_NAME = "commandName";

    @Mock
    BindingContext bindingContext;
    @Mock
    Function function;
    private CommandAttribute attribute = aCommandAttribute(COMMAND_NAME);

    @Before
    public void setup() {
	initMocks(this);
    }

    @Theory
    @Test(expected = MalformedAttributeException.class)
    public void whenCreateWithIllegalAttributeValue_thenThrowException(String illegalAttributeValue) {
	aCommandAttribute(illegalAttributeValue);
    }

    @Test
    public void givenFunctionWithParameters_whenFind_thenReturnCommandWithParametersSupported() {
	when(bindingContext.findFunction(COMMAND_NAME, withParameterTypes())).thenReturn(function);

	Command command = attribute.findCommand(bindingContext, withParameterTypes());

	assertNotNull(command);
	assertTrue(command.supportsPreferredParameterType);
    }

    @Test
    public void givenFunctionWithoutParameters_whenFind_thenReturnCommandWithoutParametersSupported() {
	when(bindingContext.findFunction(COMMAND_NAME)).thenReturn(function);

	Command command = attribute.findCommand(bindingContext);

	assertNotNull(command);
	assertFalse(command.supportsPreferredParameterType);
    }

    @Test
    public void whenFindANonExistingCommand_thenReturnNull() {
	Command command = attribute.findCommand(bindingContext);

	assertNull(command);
    }

    private Class<?>[] withParameterTypes() {
	return new Class<?>[] {Object.class};
    }
}
