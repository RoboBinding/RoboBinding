package org.robobinding.codegen.samples;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class PresentationModelWithChangeSupport extends AbstractPresentationModel implements HasPresentationModelChangeSupport{
	private PresentationModelChangeSupport changeSupport;
	
	@Override
	public PresentationModelChangeSupport getPresentationModelChangeSupport() {
		return changeSupport;
	}
}
