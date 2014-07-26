package org.robobinding.presentationmodel;

import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeSupport;
import org.robobinding.property.PropertyUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PresentationModelChangeSupport {
    private final PropertyChangeSupport propertyChangeSupport;

    PresentationModelChangeSupport(Object presentationModel) {
	propertyChangeSupport = new PropertyChangeSupport(presentationModel, 
		PropertyUtils.getPropertyNames(presentationModel.getClass()));
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public void firePropertyChange(String propertyName) {
	propertyChangeSupport.firePropertyChange(propertyName);
    }

    public void refreshPresentationModel() {
	propertyChangeSupport.fireChangeAll();
    }
}
