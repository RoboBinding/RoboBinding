package org.robobinding.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BooleanDecisionTest {
	@Test
	public void whenOrTwoFalse_thenResultIsFalse() {
		BooleanDecision decision = new BooleanDecision();
		decision.or(false).or(false);

		assertThat(decision.getResult(), is(false));
	}

	@Test
	public void whenOrFalseTrueFalse_thenResultIsTrue() {
		BooleanDecision decision = new BooleanDecision();
		decision.or(false).or(true).or(false);

		assertThat(decision.getResult(), is(true));
	}
}
