package org.robobinding.property;

import java.util.Map;

import org.robobinding.util.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyChangeSupport {
	private final PropertyValidation validation;
	private final Map<String, PropertyChangeListeners> listenerMap;

	public PropertyChangeSupport(PropertyValidation validation) {
		this.validation = validation;

		listenerMap = Maps.newHashMap();
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		validation.checkValid(propertyName);
		
		if (!listenerMap.containsKey(propertyName)) {
			listenerMap.put(propertyName, new PropertyChangeListeners());
		}

		PropertyChangeListeners listeners = listenerMap.get(propertyName);
		listeners.add(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listenerMap.containsKey(propertyName)) {
			PropertyChangeListeners listeners = listenerMap.get(propertyName);
			listeners.remove(listener);
		}
	}

	public void firePropertyChange(String propertyName) {
		validation.checkValid(propertyName);
		
		PropertyChangeListeners propertyChangeListeners = listenerMap.get(propertyName);
		if (propertyChangeListeners != null) {
			propertyChangeListeners.firePropertyChange();
		}
	}

	public void fireChangeAll() {
		for (PropertyChangeListeners propertyChangeListeners : listenerMap.values()) {
			propertyChangeListeners.firePropertyChange();
		}
	}
}
