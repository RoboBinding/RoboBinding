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
package robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.beans.BeanAdapter;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(Theories.class)
public class AbstractCommandViewAttributeTest
{
	@SuppressWarnings("unused")
	private static class DummyPresentationModel
	{
		public void w(){}
		public void x(){}
		public void x(int arg1){}
		public void y(int arg1, int arg2){}
		public void z(int arg1){}
		public void z(int arg1, double arg2){}
	}
	
	@DataPoints
	public static RequestedToExpectedCommandMapping[] mappings = {
		expectThat(requestedCommand("w"), withNoParameterTypes(), returnsCommand("w"), withNoParameterTypes()),
		expectThat(requestedCommand("w"), withSingleIntegerArg(), returnsCommand("w"), withNoParameterTypes()),
		expectThat(requestedCommand("x"), withNoParameterTypes(), returnsCommand("x"), withNoParameterTypes()),
		expectThat(requestedCommand("x"), withSingleIntegerArg(), returnsCommand("x"), withSingleIntegerArg()),
		expectThat(requestedCommand("y"), withTwoIntegerArgs(), returnsCommand("y"), withTwoIntegerArgs()),
		expectThat(requestedCommand("z"), withOneIntegerOneDoubleArg(), returnsCommand("z"), withOneIntegerOneDoubleArg()),
	};

	@DataPoints
	public static InvalidRequestedCommand[] invalidRequestedCommands = {
		expectInvalid(requestedCommand("y"), withNoParameterTypes()),
		expectInvalid(requestedCommand("y"), withSingleIntegerArg()),
		expectInvalid(requestedCommand("z"), withNoParameterTypes()),
		expectInvalid(requestedCommand("z"), withTwoIntegerArgs()),
		expectInvalid(requestedCommand("abc"), withNoParameterTypes())
	};
	
	
	private DummyPresentationModel presentationModel;
	private DummyCommandViewAttribute commandViewAttribute;
	
	@Before
	public void setUp()
	{
		presentationModel = new DummyPresentationModel();
	}
	
	@Theory
	public void shouldGetCorrectCommandFromPresentationModel(RequestedToExpectedCommandMapping mapping)
	{
		newCommandViewAttributeWith(mapping.preferredParameterTypes);
		
		commandViewAttribute.bindOnto(new BeanAdapter(presentationModel), mapping.attributeValue);
		
		assertThat(commandViewAttribute.commandMethodName(), equalTo(mapping.expectedCommandName));
		assertThat(commandViewAttribute.commandParameterTypes(), equalTo(mapping.expectedCommandParameterTypes));
	}

	@Theory
	@Test (expected=RuntimeException.class)
	public void whenBindingToAnInvalidCommandName_ShouldThrowARuntimeException(InvalidRequestedCommand requestedCommand)
	{
		newCommandViewAttributeWith(requestedCommand.preferredParameterTypes);
		commandViewAttribute.bindOnto(new BeanAdapter(presentationModel), requestedCommand.attributeValue);
	}
	
	private static class DummyCommandViewAttribute extends AbstractCommandViewAttribute
	{
		private Command command;
		private final Class<?>[] preferredCommandParameterTypes;
		
		public DummyCommandViewAttribute(Class<?>[] preferredCommandParameterTypes)
		{
			this.preferredCommandParameterTypes = preferredCommandParameterTypes;
		}

		@Override
		protected void bindOnto(Command command)
		{
			this.command = command;
		}

		@Override
		protected Class<?>[] getPreferredCommandParameterTypes()
		{
			return preferredCommandParameterTypes;
		}
		
		String commandMethodName()
		{
			return command.method.getName();
		}
		
		Class<?>[] commandParameterTypes()
		{
			return command.method.getParameterTypes();
		}
	}
	
	private abstract static class RequestedCommand
	{
		String attributeValue;
		Class<?>[] preferredParameterTypes;
		public RequestedCommand(String attributeValue, Class<?>[] preferredParameterTypes)
		{
			this.attributeValue = attributeValue;
			this.preferredParameterTypes = preferredParameterTypes;
		}
	}
	
	private static class RequestedToExpectedCommandMapping extends RequestedCommand
	{
		String expectedCommandName;
		Class<?>[] expectedCommandParameterTypes;
		
		public RequestedToExpectedCommandMapping(String attributeValue, Class<?>[] preferredParameterTypes, String expectedCommandName, Class<?>[] expectedCommandParameterTypes)
		{
			super(attributeValue, preferredParameterTypes);
			this.expectedCommandName = expectedCommandName;
			this.expectedCommandParameterTypes = expectedCommandParameterTypes;
		}
	}
	
	private static class InvalidRequestedCommand extends RequestedCommand
	{
		public InvalidRequestedCommand(String attributeValue, Class<?>[] preferredParameterTypes)
		{
			super(attributeValue, preferredParameterTypes);
		}
	}
	
	private static String returnsCommand(String commandName)
	{
		return commandName;
	}

	private static String requestedCommand(String commandName)
	{
		return commandName;
	}

	private static Class<?>[] withNoParameterTypes()
	{
		return new Class[0];
	}

	private static Class<?>[] withOneIntegerOneDoubleArg()
	{
		return new Class[]{int.class, double.class};
	}

	private static Class<?>[] withTwoIntegerArgs()
	{
		return new Class[]{int.class, int.class};
	}

	private static Class<?>[] withSingleIntegerArg()
	{
		return new Class[]{int.class};
	}
	
	private static RequestedToExpectedCommandMapping expectThat(String attributeValue, Class<?>[] preferredParameterTypes, String expectedCommandName, Class<?>[] expectedCommandParameterTypes)
	{
		return new RequestedToExpectedCommandMapping(attributeValue, preferredParameterTypes, expectedCommandName, expectedCommandParameterTypes);
	}

	private static InvalidRequestedCommand expectInvalid(String attributeValue, Class<?>[] preferredParameterTypes)
	{
		return new InvalidRequestedCommand(attributeValue, preferredParameterTypes);
	}
	
	private void newCommandViewAttributeWith(Class<?>[] preferredCommandParameterTypes)
	{
		commandViewAttribute = new DummyCommandViewAttribute(preferredCommandParameterTypes);
	}
}
