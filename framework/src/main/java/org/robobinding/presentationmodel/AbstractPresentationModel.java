package org.robobinding.presentationmodel;

import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;

/**
 * Used to implement a presentation model by extending this class instead of AspectJ annotation approach.
 * 
 * @since 1.0
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractPresentationModel implements ObservableBean {
    private final PresentationModelChangeSupport presentationModelChangeSupport;

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
    
    protected void firePropertyChange(String propertyName) {
	presentationModelChangeSupport.firePropertyChange(propertyName);
    }
    
    public void refreshPresentationModel() {
	presentationModelChangeSupport.refreshPresentationModel();
    }
}
