package org.robobinding.property;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class DefaultConstructorImpl implements ItemPresentationModelFactory {
    private final Constructor<ItemPresentationModel<Object>> itemPresentationModelConstructor;

    public DefaultConstructorImpl(Constructor<ItemPresentationModel<Object>> itemPresentationModelConstructor) {
	this.itemPresentationModelConstructor = itemPresentationModelConstructor;
    }

    @Override
    public ItemPresentationModel<Object> newItemPresentationModel() {
	try {
	    return itemPresentationModelConstructor.newInstance(new Object[0]);
	} catch (IllegalArgumentException e) {
	    throw new RuntimeException(e);
	} catch (InstantiationException e) {
	    throw new RuntimeException(e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException(e);
	}
    }
}