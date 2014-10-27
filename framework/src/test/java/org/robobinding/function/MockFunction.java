package org.robobinding.function;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockFunction implements Function {
	public boolean commandInvoked;
	public Object[] argsPassedToInvoke;
	public int invocationCount;

	@Override
	public Object call(Object... args) {
		commandInvoked = true;
		argsPassedToInvoke = args;
		invocationCount++;
		return null;
	}
}