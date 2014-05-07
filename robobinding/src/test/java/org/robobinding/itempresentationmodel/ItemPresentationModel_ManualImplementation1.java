package org.robobinding.itempresentationmodel;

import org.robobinding.property.ObservableProperties;
import org.robobinding.property.PresentationModelPropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemPresentationModel_ManualImplementation1 implements ItemPresentationModel<Object>, ObservableProperties {
    @Override
    public void addPropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PresentationModelPropertyChangeListener listener) {
    }

    @Override
    public void updateData(int index, Object bean) {
    }
}
