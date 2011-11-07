package robobinding.binding.viewattribute;

import robobinding.function.Function;

public class Command
{
	public final Function function;
	private final boolean supportsPreferredParameterType;

	public Command(Function function, boolean supportsPreferredParameterType)
	{
		this.function = function;
		this.supportsPreferredParameterType = supportsPreferredParameterType;
	}
	
	public void invoke(Object arg)
	{
		if(supportsPreferredParameterType)
			function.call(arg);
		else
			function.call();
	}
}