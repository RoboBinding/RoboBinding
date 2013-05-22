/**
 * Copyright 2013 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewUpdatePropagationLatchTest {
    private ViewUpdatePropagationLatch latch = new ViewUpdatePropagationLatch();

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
