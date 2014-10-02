package org.robobinding.presentationmodel;

import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeSupport;
import org.robobinding.property.PropertyUtils;
import org.robobinding.property.PropertyValidation;

import com.google.common.base.Preconditions;

/**
 * To notify presentation model changes.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PresentationModelChangeSupport {
	private final PropertyChangeSupport propertyChangeSupport;

	public PresentationModelChangeSupport(Object presentationModel) {
		this(checkAndReturnClass(presentationModel));
	}
	
	private static Class<?> checkAndReturnClass(Object presentationModel) {
		Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
		return presentationModel.getClass();
	}

	public PresentationModelChangeSupport(Class<?> presentationModelClass) {
		Preconditions.checkNotNull(presentationModelClass, "presentationModelClass must not be null");
		
		PropertyValidation propertyValidation = new PropertyValidation(presentationModelClass, 
				PropertyUtils.getPropertyNames(presentationModelClass));
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
