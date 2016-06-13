package org.robobinding.property;

import java.util.Set;

import org.robobinding.util.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyChangeListeners {
	private final Set<PropertyChangeListener> listeners;

	public PropertyChangeListeners() {
		this.listeners = Sets.newLinkedHashSet();
	}

	public void add(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	public boolean remove(PropertyChangeListener listener) {
		return listeners.remove(listener);
	}

	public boolean contains(PropertyChangeListener listener) {
		return listeners.contains(listener);
	}

	public void firePropertyChange() {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChanged();
		}
	}
}
