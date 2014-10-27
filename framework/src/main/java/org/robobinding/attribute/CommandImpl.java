package org.robobinding.attribute;

import org.robobinding.function.Function;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class CommandImpl implements Command {
	public final Function function;
	final boolean supportsPreferredParameterType;

	public CommandImpl(Function function, boolean supportsPreferredParameterType) {
		this.function = function;
		this.supportsPreferredParameterType = supportsPreferredParameterType;
	}

	@Override
	public Object invoke(Object arg) {
		if (supportsPreferredParameterType)
			return function.call(arg);
		else
			return function.call();
	}
}
