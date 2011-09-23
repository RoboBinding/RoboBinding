package robobinding.value;

import java.beans.PropertyChangeListener;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public interface ValueModel<T>
{
    T getValue();
    void setValue(T newValue);
    void addValueChangeListener(PropertyChangeListener listener);
    void removeValueChangeListener(PropertyChangeListener listener);
}
