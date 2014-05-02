package org.robobinding.property;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.robobinding.util.Preconditions.checkNotBlank;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeSupport {
    private Object bean;
    private Set<String> availablePropertyNames;
    private Map<String, PresentationModelPropertyChangeListeners> propertyToItsChangeListeners;

    public PresentationModelPropertyChangeSupport(Object bean) {
	checkNotNull(bean, "Bean should not be null");

	this.bean = bean;
	initializeAvailableProperties();

	propertyToItsChangeListeners = Maps.newHashMap();
    }

    private void initializeAvailableProperties() {
	availablePropertyNames = Sets.newHashSet(PropertyUtils.getPropertyNames(bean.getClass()));
    }

    public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	validatePropertyName(propertyName);
	if (!propertyToItsChangeListeners.containsKey(propertyName)) {
	    propertyToItsChangeListeners.put(propertyName, new PresentationModelPropertyChangeListeners());
	}

	PresentationModelPropertyChangeListeners listeners = propertyToItsChangeListeners.get(propertyName);
	listeners.add(listener);
    }

    public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
	if (propertyToItsChangeListeners.containsKey(propertyName)) {
	    PresentationModelPropertyChangeListeners listeners = propertyToItsChangeListeners.get(propertyName);
	    listeners.remove(listener);
	}
    }

    PresentationModelPropertyChangeListeners getPropertyChangeListeners(String propertyName) {
	if (propertyToItsChangeListeners.containsKey(propertyName)) {
	    return propertyToItsChangeListeners.get(propertyName);
	}
	return PresentationModelPropertyChangeListeners.empty();
    }

    public void firePropertyChange(String propertyName) {
	validatePropertyName(propertyName);
	PresentationModelPropertyChangeListeners propertyChangeListeners = propertyToItsChangeListeners.get(propertyName);
	if (propertyChangeListeners != null) {
	    propertyChangeListeners.firePropertyChange();
	}
    }

    private void validatePropertyName(String propertyName) {
	checkNotBlank(propertyName, "propertyName cannot be empty");
	checkArgument(availablePropertyNames.contains(propertyName), "Bean '" + getBeanClassName() + "' does not contain given property'"
		+ propertyName + "'");
    }

    private String getBeanClassName() {
	return bean.getClass().getName();
    }

    public void fireChangeAll() {
	for (PresentationModelPropertyChangeListeners propertyChangeListeners : propertyToItsChangeListeners.values()) {
	    propertyChangeListeners.firePropertyChange();
	}
    }
}
