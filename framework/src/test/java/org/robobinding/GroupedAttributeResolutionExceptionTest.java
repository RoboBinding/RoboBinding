package org.robobinding;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupedAttributeResolutionExceptionTest {
	@Test
	public void byDefaultShouldNotHaveErrors() {
		GroupedAttributeResolutionException exception = new GroupedAttributeResolutionException();

		exception.assertNoErrors();
		assertThat(exception.getAttributeResolutionExceptions(), hasSize(0));
	}

	@Test
	public void givenErrorsHaveBeenAddedShouldThrowExceptionOnAssert() {
		GroupedAttributeResolutionException exception = new GroupedAttributeResolutionException();
		exception.add(new AttributeResolutionException("attribute"));

		try {
			exception.assertNoErrors();
			fail();
		} catch (GroupedAttributeResolutionException e) {
			assertThat(e.getAttributeResolutionExceptions(), hasSize(1));
		}
	}
}
