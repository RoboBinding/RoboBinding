package org.robobinding.presentationmodel;

import java.util.Set;

import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeSupport;
import org.robobinding.property.PropertyUtils;
import org.robobinding.property.PropertyValidation;
import org.robobinding.util.Preconditions;

/**
 * To notify presentation model changes.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PresentationModelChangeSupport {
	private PropertyChangeSupport propertyChangeSupport;

	public PresentationModelChangeSupport(Object presentationModel) {
		Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
		initialize(presentationModel.getClass(), PropertyUtils.getPropertyNames(presentationModel.getClass()));
	}
	
	PresentationModelChangeSupport(Class<?> presentationModelClass, Set<String> propertyNames) {
		initialize(presentationModelClass, propertyNames);
	}

	private void initialize(Class<?> presentationModelClass, Set<String> propertyNames) {
		PropertyValidation propertyValidation = new PropertyValidation(presentationModelClass, propertyNames);
		propertyChangeSupport = new PropertyChangeSupport(propertyValidation);
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
