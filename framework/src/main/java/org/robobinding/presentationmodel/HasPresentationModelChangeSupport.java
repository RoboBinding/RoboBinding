package org.robobinding.presentationmodel;


/**
 * When a PresentationModel needs a {@link PresentationModelChangeSupport} to notify property changes, 
 * the PresentationModel has to implement the interface. This will guarantee the same {@link PresentationModelChangeSupport} 
 * instance is used by the framework internally.
 * 
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface HasPresentationModelChangeSupport {
	PresentationModelChangeSupport getPresentationModelChangeSupport();
}
