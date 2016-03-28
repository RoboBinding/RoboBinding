package org.robobinding.attribute;

import org.robobinding.BindingContext;
import org.robobinding.function.Function;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class EventAttribute extends AbstractAttribute {
	private String commandName;

	public EventAttribute(String name, String value) {
		super(name);

		if (curlyBracesArePresentIn(value)) {
			throw new MalformedAttributeException(name, "Curly braces should be used for binding to properties. "
					+ "Event handling invokes methods on your presentation model, and there is no method called '" + value + "'");
		}

		this.commandName = value;
	}

	private boolean curlyBracesArePresentIn(String commandName) {
		return commandName.contains("{") || commandName.contains("}");
	}

	public Command findCommand(BindingContext bindingContext, Class<?>... parameterTypes) {
		Function function = bindingContext.findFunction(commandName, parameterTypes);

		if (function != null) {
			return isArrayNotEmpty(parameterTypes) ? new CommandImpl(function, true) : new CommandImpl(function, false);
		} else {
			return null;
		}
	}

	private static <T> boolean isArrayNotEmpty(final T[] array) {
		return (array != null && array.length != 0);
	}

	public String getCommandName() {
		return commandName;
	}
}
