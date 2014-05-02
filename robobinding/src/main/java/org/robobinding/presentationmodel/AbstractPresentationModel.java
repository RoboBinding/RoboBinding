package org.robobinding.presentationmodel;

import org.robobinding.property.ObservableProperties;
import org.robobinding.property.PresentationModelPropertyChangeListener;

/**
 * @since 1.0
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractPresentationModel implements ObservableProperties {
    protected PresentationModelChangeSupport presentationModelChangeSupport;

    public AbstractPresentationModel() {
	presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	presentationModelChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	presentationModelChangeSupport.removePropertyChangeListener(propertyName, listener);
    }
}
