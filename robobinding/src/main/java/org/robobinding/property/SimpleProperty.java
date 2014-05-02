package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class SimpleProperty<T> extends AbstractProperty<T> {
    public SimpleProperty(ObservableBean observableBean, PropertyAccessor<T> propertyAccessor) {
	super(observableBean, propertyAccessor);
    }
}
