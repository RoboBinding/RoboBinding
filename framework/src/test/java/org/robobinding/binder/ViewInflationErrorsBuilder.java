package org.robobinding.binder;

import static org.mockito.Mockito.mock;

import org.robobinding.AttributeResolutionException;
import org.robobinding.ViewResolutionErrorsException;
import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.viewattribute.AttributeBindingException;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewInflationErrorsBuilder {
	private String viewName;
	private ViewResolutionErrorsException viewResolutionErrors;
	private ViewBindingErrors viewBindingErrors;

	private ViewInflationErrorsBuilder(String viewName) {
		this.viewName = viewName;

		View view = mock(View.class);
		viewResolutionErrors = new ViewResolutionErrorsException(view);
		viewBindingErrors = new ViewBindingErrors(view);
	}

	public static ViewInflationErrorsBuilder aViewInflationErrors(String viewName) {
		return new ViewInflationErrorsBuilder(viewName);
	}

	public ViewInflationErrorsBuilder withAttributeResolutionErrorOf(String attribute, String errorMessage) {
		viewResolutionErrors.addAttributeError(new AttributeResolutionException(attribute, errorMessage));
		return this;
	}

	public ViewInflationErrorsBuilder withMissingRequiredAttributesResolutionErrorOf(String... missingAttributes) {
		viewResolutionErrors.addMissingRequiredAttributeError(new MissingRequiredAttributesException(Lists.newArrayList(missingAttributes)));
		return this;
	}

	public ViewInflationErrorsBuilder withAttributeBindingErrorOf(String attribute, String errorMessage) {
		viewBindingErrors.addAttributeError(new AttributeBindingException(attribute, new RuntimeException(errorMessage)));
		return this;
	}

	public ViewInflationErrors build() {
		ViewInflationErrors error = new ViewInflationErrors(viewResolutionErrors) {
			@Override
			public String getViewName() {
				return viewName;
			}
		};
		error.setBindingErrors(viewBindingErrors);
		return error;
	}

}
