package org.robobinding.presentationmodel;

import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;

/**
 * @since 1.0
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractPresentationModel implements ObservableBean {
    protected final PresentationModelChangeSupport presentationModelChangeSupport;

    public AbstractPresentationModel() {
	presentationModelChangeSupport = new PresentationModelChangeSupport(this);
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	presentationModelChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	presentationModelChangeSupport.removePropertyChangeListener(propertyName, listener);
    }
}
