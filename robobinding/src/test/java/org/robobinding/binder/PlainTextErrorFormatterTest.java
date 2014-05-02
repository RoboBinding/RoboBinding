package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.binder.ViewInflationErrorsBuilder.aViewInflationErrors;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PlainTextErrorFormatterTest {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String DOUBLE_LINE_SEPARATOR = LINE_SEPARATOR + LINE_SEPARATOR;
    private PlainTextErrorFormatter errorFormatter;

    @Before
    public void setUp() {
	errorFormatter = new PlainTextErrorFormatter();
    }

    @Test
    public void whenFormatAGivenInflationError_thenReceivedAnExpectedMessage() {
	String message = errorFormatter.format(aViewInflationErrors("CustomView").withAttributeResolutionErrorOf("text", "invalid syntax ${prop1")
		.withMissingRequiredAttributesResolutionErrorOf("source", "dropdownLayout")
		.withAttributeBindingErrorOf("visibility", "unmatch presentationModel.prop2 type").build());

	assertThat(message, equalTo("-------------------------CustomView(3 errors)-----------------------" + LINE_SEPARATOR
		+ "text: invalid syntax ${prop1" + DOUBLE_LINE_SEPARATOR + "Missing attributes: source, dropdownLayout" + DOUBLE_LINE_SEPARATOR
		+ "visibility: unmatch presentationModel.prop2 type" + LINE_SEPARATOR));
    }
}
