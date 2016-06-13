package org.robobinding;

import org.robobinding.presentationmodel.PresentationModelAdapter;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface BindingContextFactory {
	BindingContext create(PresentationModelAdapter presentationModel);
}
