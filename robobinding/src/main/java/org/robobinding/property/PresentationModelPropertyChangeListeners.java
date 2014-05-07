package org.robobinding.property;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeListeners {
    List<PresentationModelPropertyChangeListener> listeners;

    public PresentationModelPropertyChangeListeners() {
	this.listeners = Lists.newArrayList();
    }

    public void add(PresentationModelPropertyChangeListener propertyChangeListener) {
	checkNotNull(propertyChangeListener, "propertyChangeListener cannot be null");
	listeners.add(propertyChangeListener);
    }

    public boolean remove(PresentationModelPropertyChangeListener listener) {
	return listeners.remove(listener);
    }

    public boolean contains(PresentationModelPropertyChangeListener listener) {
	return listeners.contains(listener);
    }

    public void firePropertyChange() {
	for (PresentationModelPropertyChangeListener listener : listeners) {
	    listener.propertyChanged();
	}
    }

    private static final PresentationModelPropertyChangeListeners EMPTY = new PresentationModelPropertyChangeListeners();

    public static PresentationModelPropertyChangeListeners empty() {
	return EMPTY;
    }
}
