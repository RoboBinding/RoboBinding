package org.robobinding.attribute;

import org.robobinding.function.Function;
import org.robobinding.presentationmodel.PresentationModelAdapter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class CommandAttribute extends AbstractAttribute {
    private String commandName;

    public CommandAttribute(String name, String value) {
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

    public Command findCommand(PresentationModelAdapter presentationModelAdapter, Class<?>... parameterTypes) {
	Function function = presentationModelAdapter.findFunction(commandName, parameterTypes);

	if (function != null) {
	    return isArrayNotEmpty(parameterTypes) ? new Command(function, true) : new Command(function, false);
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
