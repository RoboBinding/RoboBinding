package org.robobinding.codegen.samples;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelWithChangeSupport_PM extends AbstractPresentationModel_PM {
	public PresentationModelWithChangeSupport_PM(PresentationModelWithChangeSupport presentationModel) {
		super(presentationModel, presentationModel.getPresentationModelChangeSupport());
	}
}
