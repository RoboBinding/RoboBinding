package org.robobinding.widget;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.robobinding.attribute.Command;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class EventCommand implements Command {
	private final Object returnValue;
	public Object lastArg;
	public int invocationCount;
	
	public EventCommand(Object returnValue) {
		this.returnValue = returnValue;
	}
	
	public EventCommand() {
		this(null);
	}

	@Override
	public Object invoke(Object arg) {
		lastArg = arg;
		invocationCount++;
		return returnValue;
	}

	public void assertEventReceived(Class<?> expectedEventClass) {
		assertTrue("No event has been received yet", invocationCount >= 1);
		assertThat(lastArg, instanceOf(expectedEventClass));
	}

	@SuppressWarnings("unchecked")
	public <T> T getEventReceived() {
		return (T) lastArg;
	}

	public void assertTimesOfEventReceived(int expectedTimes) {
		assertThat(invocationCount, is(expectedTimes));
	}
}
