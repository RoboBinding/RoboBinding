package org.robobinding.binder;

import java.util.Collection;
import java.util.Map;

import org.robobinding.ViewResolutionErrors;
import org.robobinding.util.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewHierarchyInflationErrorsException extends RuntimeException {
	private Map<Object, ViewInflationErrors> errorMap;
	private String errorMessage;

	ViewHierarchyInflationErrorsException() {
		errorMap = Maps.newLinkedHashMap();
	}

	void addViewResolutionError(ViewResolutionErrors error) {
		errorMap.put(error.getView(), new ViewInflationErrors(error));
	}

	void addViewBindingError(ViewBindingErrors error) {
		try {
			ViewInflationErrors inflationError = errorMap.get(error.getView());
			inflationError.setBindingErrors(error);
		} catch (NullPointerException e) {
			throw e;
		}
	}

	void assertNoErrors(ErrorFormatter errorFormatter) {
		StringBuilder sb = new StringBuilder();
		for (ViewInflationErrors error : errorMap.values()) {
			if (error.hasErrors()) {
				appendln(sb, errorFormatter.format(error));
			}
		}

		if (sb.length() != 0) {
			errorMessage = sb.toString();
			throw this;
		}
	}

	private static void appendln(StringBuilder sb, String str) {
		sb.append(str);
		sb.append("\r\n");
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}

	public Collection<ViewInflationErrors> getErrors() {
		return errorMap.values();
	}

	protected interface ErrorFormatter {
		String format(ViewInflationErrors error);
	}

}
