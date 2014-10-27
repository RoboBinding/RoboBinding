package org.robobinding.property;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 * 
 */
public interface ObservableBean {
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
