package robobinding.binding.viewattribute;

import robobinding.property.Command;

class MockCommand extends Command
{
	boolean commandInvoked;
	Object[] argsPassedToInvoke;
	
	MockCommand()
	{
		super(null, null);
	}
	
	@Override
	public void invoke(Object... args)
	{
		commandInvoked = true;
		argsPassedToInvoke = args;
	}
}