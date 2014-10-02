package org.robobinding.codegen.samples;

import org.robobinding.presentationmodel.PresentationModelChangeSupport;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelWithoutChangeSupport_PM extends AbstractPresentationModel_PM {
	public PresentationModelWithoutChangeSupport_PM(PresentationModelWithChangeSupport presentationModel) {
		super(presentationModel, new PresentationModelChangeSupport(presentationModel));
	}
}
