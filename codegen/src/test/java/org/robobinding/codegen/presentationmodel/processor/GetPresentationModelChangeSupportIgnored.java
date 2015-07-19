package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class GetPresentationModelChangeSupportIgnored implements HasPresentationModelChangeSupport{
	@Override
	public PresentationModelChangeSupport getPresentationModelChangeSupport() {
		return null;
	}
}
