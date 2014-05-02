package org.robobinding.attribute;

import org.robobinding.function.Function;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class Command {
    public final Function function;
    final boolean supportsPreferredParameterType;

    Command(Function function, boolean supportsPreferredParameterType) {
	this.function = function;
	this.supportsPreferredParameterType = supportsPreferredParameterType;
    }

    public void invoke(Object arg) {
	if (supportsPreferredParameterType)
	    function.call(arg);
	else
	    function.call();
    }
}