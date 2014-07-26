package org.robobinding.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class FactoryMethodImpl implements ItemPresentationModelFactory {
    private final Object owner;
    private final Method factoryMethod;

    public FactoryMethodImpl(Object owner, Method factoryMethod) {
	this.owner = owner;
	this.factoryMethod = factoryMethod;
    }

    @Override
    public ItemPresentationModel<Object> newItemPresentationModel() {
	try {
	    @SuppressWarnings("unchecked")
	    ItemPresentationModel<Object> itemPresentationModel = (ItemPresentationModel<Object>) factoryMethod.invoke(owner, new Object[0]);
	    return itemPresentationModel;
	} catch (IllegalArgumentException e) {
	    throw new RuntimeException(e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException(e);
	}
    }

}
