package org.robobinding.property;

import static org.robobinding.util.Preconditions.checkNotBlank;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyChangeSupport {
    private final Object bean;
    private final Set<String> existingPropertyNames;
    private final Map<String, PropertyChangeListeners> propertyChangeListenerMap;

    public PropertyChangeSupport(Object bean, Set<String> existingPropertyNames) {
	this.bean = bean;
	this.existingPropertyNames = existingPropertyNames;

	propertyChangeListenerMap = Maps.newHashMap();
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	validatePropertyName(propertyName);
	if (!propertyChangeListenerMap.containsKey(propertyName)) {
	    propertyChangeListenerMap.put(propertyName, new PropertyChangeListeners());
	}

	PropertyChangeListeners listeners = propertyChangeListenerMap.get(propertyName);
	listeners.add(listener);
    }

    private void validatePropertyName(String propertyName) {
        checkNotBlank(propertyName, "propertyName cannot be empty");
        Preconditions.checkArgument(existingPropertyNames.contains(propertyName), "Bean '" + getBeanClassName() + "' does not contain given property'"
        	+ propertyName + "'");
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	if (propertyChangeListenerMap.containsKey(propertyName)) {
	    PropertyChangeListeners listeners = propertyChangeListenerMap.get(propertyName);
	    listeners.remove(listener);
	}
    }

    public void firePropertyChange(String propertyName) {
	validatePropertyName(propertyName);
	PropertyChangeListeners propertyChangeListeners = propertyChangeListenerMap.get(propertyName);
	if (propertyChangeListeners != null) {
	    propertyChangeListeners.firePropertyChange();
	}
    }

    private String getBeanClassName() {
	return bean.getClass().getName();
    }

    public void fireChangeAll() {
	for (PropertyChangeListeners propertyChangeListeners : propertyChangeListenerMap.values()) {
	    propertyChangeListeners.firePropertyChange();
	}
    }
}
