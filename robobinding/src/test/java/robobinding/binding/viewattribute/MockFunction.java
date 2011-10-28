package robobinding.binding.viewattribute;

import robobinding.beans.Function;

class MockFunction implements Function
{
	boolean commandInvoked;
	Object[] argsPassedToInvoke;
	
	@Override
	public void call(Object... args)
	{
		commandInvoked = true;
		argsPassedToInvoke = args;
	}
}