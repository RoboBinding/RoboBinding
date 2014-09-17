package org.robobinding.attribute;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robobinding.attribute.Attributes.anEventAttribute;

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
public class EventAttributeTest {
	@DataPoints
	public static String[] illegalAttributeValues = { "{invalid_command_name}", "{invalid_command_name", "invalid_command_name}" };

	private static final String COMMAND_NAME = "commandName";

	@Mock
	BindingContext bindingContext;
	@Mock
	Function function;
	private EventAttribute attribute = anEventAttribute(COMMAND_NAME);

	@Before
	public void setup() {
		initMocks(this);
	}

	@Theory
	@Test(expected = MalformedAttributeException.class)
	public void whenCreateWithIllegalAttributeValue_thenThrowException(String illegalAttributeValue) {
		anEventAttribute(illegalAttributeValue);
	}

	@Test
	public void givenFunctionWithParameters_whenFind_thenReturnCommandWithParametersSupported() {
		when(bindingContext.findFunction(COMMAND_NAME, withParameterTypes())).thenReturn(function);

		CommandImpl command = (CommandImpl) attribute.findCommand(bindingContext, withParameterTypes());

		assertNotNull(command);
		assertTrue(command.supportsPreferredParameterType);
	}

	@Test
	public void givenFunctionWithoutParameters_whenFind_thenReturnCommandWithoutParametersSupported() {
		when(bindingContext.findFunction(COMMAND_NAME)).thenReturn(function);

		CommandImpl command = (CommandImpl) attribute.findCommand(bindingContext);

		assertNotNull(command);
		assertFalse(command.supportsPreferredParameterType);
	}

	@Test
	public void whenFindANonExistingCommand_thenReturnNull() {
		Command command = attribute.findCommand(bindingContext);

		assertNull(command);
	}

	private Class<?>[] withParameterTypes() {
		return new Class<?>[] { Object.class };
	}
}
