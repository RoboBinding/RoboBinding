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
import static org.robobinding.attribute.Attributes.aCommandAttribute;
import static org.robobinding.viewattribute.MockPresentationModelAdapterBuilder.aPresentationModelAdapter;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class CommandAttributeTest
{
	@DataPoints
	public static String[] illegalAttributeValues = {"{invalid_command_name}", "{invalid_command_name", "invalid_command_name}"};
	
	private static final String COMMAND_NAME = "commandName";
	
	@Theory@Test(expected=MalformedAttributeException.class)
	public void givenIllegalAttributeValues(String illegalAttributeValue)
	{
		aCommandAttribute(illegalAttributeValue);
	}
	
	@Test
	public void givenACommandAttributeWithParameters_whenFind_thenReturnCommandWithParametersSupported()
	{
		CommandAttribute attribute = aCommandAttribute(COMMAND_NAME);
		
		Command command = attribute.findCommand(
				aPresentationModelAdapter().withFunction(COMMAND_NAME, withParameterTypes()).build(), 
				withParameterTypes());
		
		assertNotNull(command);
		assertTrue(command.supportsPreferredParameterType);
	}
	
	@Test
	public void givenACommandAttributeWithoutParameters_whenFind_thenReturnCommandWithoutParametersSupported()
	{
		CommandAttribute attribute = aCommandAttribute(COMMAND_NAME);
		
		Command command = attribute.findCommand(
				aPresentationModelAdapter().withFunction(COMMAND_NAME).build());
		
		assertNotNull(command);
		assertFalse(command.supportsPreferredParameterType);
	}
	
	@Test
	public void whenFindANonExistingCommand_thenReturnNull()
	{
		CommandAttribute attribute = aCommandAttribute(COMMAND_NAME);
		
		Command command = attribute.findCommand(
				aPresentationModelAdapter().build());
		
		assertNull(command);
	}
	
	private Class<?>[] withParameterTypes()
	{
		return new Class<?>[]{Object.class};
	}
}
