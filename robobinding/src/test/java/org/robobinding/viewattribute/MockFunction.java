package org.robobinding.viewattribute;

import org.robobinding.function.Function;

public class MockFunction implements Function
{
	public boolean commandInvoked;
	public Object[] argsPassedToInvoke;
	public int invocationCount;	
	
	@Override
	public void call(Object... args)
	{
		commandInvoked = true;
		argsPassedToInvoke = args;
		invocationCount++;
	}
}