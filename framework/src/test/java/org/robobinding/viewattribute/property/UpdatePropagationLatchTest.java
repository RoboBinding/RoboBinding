package org.robobinding.viewattribute.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.robobinding.util.RandomValues;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class UpdatePropagationLatchTest {
	private UpdatePropagationLatch latch = new UpdatePropagationLatch();

	@Test
	public void givenLatchIsOn_whenTryToPass_thenFailed() {
		latch.turnOn();

		assertFalse(latch.tryToPass());
	}

	@Test
	public void givenLatchIsOn_whenTryToPassAfterFirstAttempt_thenAllAreSuccessful() {
		latch.turnOn();

		latch.tryToPass();
		assertAllTryToPassAttemptsAreSuccessful(anyNumAttempts());
	}

	private void assertAllTryToPassAttemptsAreSuccessful(int times) {
		assertTrue(latch.tryToPass());
	}

	private int anyNumAttempts() {
		return RandomValues.nextInt(10);
	}

	@Test
	public void givenLatchIsOff_whenTryToPass_thenAllAreSuccessful() {
		latch.turnOff();

		assertAllTryToPassAttemptsAreSuccessful(anyNumAttempts());
	}
}
