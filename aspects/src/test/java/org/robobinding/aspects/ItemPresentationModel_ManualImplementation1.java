package org.robobinding.aspects;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.PropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemPresentationModel_ManualImplementation1 implements ItemPresentationModel<Object>, ObservableBean {
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    }

    @Override
    public void updateData(int index, Object bean) {
    }
}
