package org.robobinding.binder;

import java.util.Collection;
import java.util.List;

import org.robobinding.ViewResolutionErrors;
import org.robobinding.util.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewInflationErrors {
	private Object view;
	private ViewResolutionErrors resolutionErrors;
	private ViewBindingErrors bindingErrors;

	public ViewInflationErrors(ViewResolutionErrors resolutionError) {
		this.view = resolutionError.getView();
		this.resolutionErrors = resolutionError;
	}

	void setBindingErrors(ViewBindingErrors bindingError) {
		this.bindingErrors = bindingError;
	}

	public boolean hasErrors() {
		return resolutionErrors.hasErrors() || bindingErrors.hasErrors();
	}

	public Object getView() {
		return view;
	}

	public ViewResolutionErrors getResolutionErrors() {
		return resolutionErrors;
	}

	public ViewBindingErrors getBindingErrors() {
		return bindingErrors;
	}

	public int numErrors() {
		return resolutionErrors.numErrors() + bindingErrors.numErrors();
	}

	public String getViewName() {
		return view.getClass().getSimpleName();
	}

	public Collection<Exception> getErrors() {
		List<Exception> errors = Lists.newArrayList();
		errors.addAll(resolutionErrors.getErrors());
		errors.addAll(bindingErrors.getAttributeErrors());
		return errors;
	}

}
