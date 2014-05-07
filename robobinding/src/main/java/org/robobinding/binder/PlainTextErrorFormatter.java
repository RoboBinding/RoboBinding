package org.robobinding.binder;

import java.text.MessageFormat;
import java.util.Collection;

import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class PlainTextErrorFormatter implements ErrorFormatter {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    public String format(ViewInflationErrors inflationError) {
	ErrorMessageBuilder errorMessageBuilder = new ErrorMessageBuilder(inflationError);
	return errorMessageBuilder.build();
    }

    private static class ErrorMessageBuilder {
	private ViewInflationErrors inflationError;
	private StringBuilder errorMessage;

	public ErrorMessageBuilder(ViewInflationErrors inflationError) {
	    this.inflationError = inflationError;
	}

	public String build() {
	    errorMessage = new StringBuilder();

	    addHeader();
	    addBody();

	    return errorMessage.toString();
	}

	private void addHeader() {
	    errorMessage.append(withNewLine(MessageFormat.format("-------------------------{0}({1} errors)-----------------------",
		    inflationError.getViewName(), inflationError.numErrors())));
	}

	private void addBody() {
	    Collection<Exception> errors = inflationError.getErrors();
	    for (Exception error : errors) {
		errorMessage.append(withNewLine(error.toString()));
		errorMessage.append(newLine());
	    }

	    if (!errors.isEmpty()) {
		removeLastNewLine();
	    }
	}

	private void removeLastNewLine() {
	    int endIndex = errorMessage.length();
	    int startIndex = endIndex - lengthOfNewLineText();
	    errorMessage.delete(startIndex, endIndex);
	}

	private int lengthOfNewLineText() {
	    return newLine().length();
	}

	private String withNewLine(String str) {
	    return str + newLine();
	}

	private String newLine() {
	    return LINE_SEPARATOR;
	}
    }
}
