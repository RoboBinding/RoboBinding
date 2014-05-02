package org.robobinding.property;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 * 
 */
public interface ObservableProperties {
    void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener);
}
