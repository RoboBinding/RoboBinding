package org.robobinding.property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;
import org.robobinding.util.MethodUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class FactoryMethodImpl<T> implements ItemPresentationModelFactory<T> {
    private Object object;
    private Class<? extends ItemPresentationModel<T>> itemPresentationModelClass;
    private Method factoryMethod;

    public FactoryMethodImpl(Object object, Class<? extends ItemPresentationModel<T>> itemPresentationModelClass, String methodName) {
	this.object = object;
	this.itemPresentationModelClass = itemPresentationModelClass;
	initializeFactoryMethod(methodName);
    }

    private void initializeFactoryMethod(String methodName) {
	factoryMethod = MethodUtils.getAccessibleMethod(object.getClass(), methodName, new Class<?>[0]);
	if (factoryMethod == null) {
	    throw new RuntimeException("FactoryMethod '" + describeExpectedFactoryMethod(methodName) + "' does not exist");
	} else {
	    validateReturnType(methodName);
	}
    }

    private String describeExpectedFactoryMethod(String methodName) {
	String className = object.getClass().getName();
	String itemPresentationModelClassName = itemPresentationModelClass.getName();
	return "public " + itemPresentationModelClassName + "[or subclass] " + className + "." + methodName + "()";
    }

    private void validateReturnType(String methodName) {
	Class<?> returnType = factoryMethod.getReturnType();
	if (!itemPresentationModelClass.isAssignableFrom(returnType)) {
	    String returnTypeName = returnType.getName();
	    throw new RuntimeException("Expected FactoryMethod '" + describeExpectedFactoryMethod(methodName)
		    + "' but has invalid actual returnType '" + returnTypeName + "'");
	}
    }

    @Override
    public ItemPresentationModel<T> newItemPresentationModel() {
	try {
	    @SuppressWarnings("unchecked")
	    ItemPresentationModel<T> itemPresentationModel = (ItemPresentationModel<T>) factoryMethod.invoke(object, new Object[0]);
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
