package org.robobinding.binder;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.robobinding.binder.ViewInflationErrorsBuilder.aViewInflationErrors;

import org.junit.Test;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ErrorFormatterWithFirstErrorStackTraceTest {
	@Test
	public void whenFormatAGivenInflationError_thenReceivedAnExpectedMessage() {
		ErrorFormatter errorFormatter = new ErrorFormatterWithFirstErrorStackTrace();

		String message = errorFormatter.format(aViewInflationErrors("CustomView").withAttributeResolutionErrorOf("text", "invalid syntax ${prop1")
				.withMissingRequiredAttributesResolutionErrorOf("source", "dropdownLayout")
				.withAttributeBindingErrorOf("visibility", "unmatch presentationModel.prop2 type").build());

		assertThat(message, containsString("CustomView(3 errors)"));
		assertThat(message, containsString("text: invalid syntax ${prop1"));
		assertThat(message, containsString("Missing attributes: source, dropdownLayout"));
		assertThat(message, containsString("visibility: unmatch presentationModel.prop2 type"));
		assertThat(message, containsString("The first error stack trace"));
	}
}
