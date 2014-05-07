package org.robobinding.presentationmodel;

import org.robobinding.property.PresentationModelPropertyChangeListener;
import org.robobinding.property.PresentationModelPropertyChangeSupport;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PresentationModelChangeSupport {
    private PresentationModelPropertyChangeSupport propertyChangeSupport;

    public PresentationModelChangeSupport(Object presentationModel) {
	propertyChangeSupport = new PresentationModelPropertyChangeSupport(presentationModel);
    }

    public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public void firePropertyChange(String propertyName) {
	propertyChangeSupport.firePropertyChange(propertyName);
    }

    public void fireChangeAll() {
	propertyChangeSupport.fireChangeAll();
    }
}
