package org.robobinding.codegen;

import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefineConstructorWithChangeSupport implements HasPresentationModelChangeSupport {
	@Override
	public PresentationModelChangeSupport getPresentationModelChangeSupport() {
		return null;
	}
}
