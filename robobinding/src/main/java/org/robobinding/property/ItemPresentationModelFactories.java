package org.robobinding.property;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemPresentationModelFactory;
import org.robobinding.util.ConstructorUtils;
import org.robobinding.util.MethodUtils;

import com.google.common.base.Strings;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemPresentationModelFactories {
    private final Object bean;

    public ItemPresentationModelFactories(Object bean) {
        this.bean = bean;
    }

    public ItemPresentationModelFactory create(PropertyAccessor propertyAccessor) {
	org.robobinding.presentationmodel.ItemPresentationModel annotation = propertyAccessor
		.getAnnotation(org.robobinding.presentationmodel.ItemPresentationModel.class);
	
	@SuppressWarnings("unchecked")
	Class<ItemPresentationModel<Object>> itemPresentationModelClass = (Class<ItemPresentationModel<Object>>) annotation.value();
	String factoryMethodName = annotation.factoryMethod();
	if (Strings.isNullOrEmpty(factoryMethodName)) {
	    return new DefaultConstructorImpl(getDefaultConstructor(itemPresentationModelClass));
	} else {
	    return new FactoryMethodImpl(bean, getFactoryMethod(factoryMethodName, itemPresentationModelClass));
	}
    }
    
    private Constructor<ItemPresentationModel<Object>> getDefaultConstructor(Class<ItemPresentationModel<Object>> itemPresentationModelClass) {
	Constructor<ItemPresentationModel<Object>> itemPresentationModelConstructor = 
		ConstructorUtils.getAccessibleConstructor(itemPresentationModelClass, new Class<?>[0]);
	if (itemPresentationModelConstructor == null) {
	    String className = itemPresentationModelClass.getName();
	    throw new RuntimeException("itemPresentationModelClass '" + className
			+ "' does not have an accessible default constructor");
	}
	return itemPresentationModelConstructor;
    }


    private Method getFactoryMethod(String methodName, Class<ItemPresentationModel<Object>> itemPresentationModelClass) {
	Method factoryMethod = MethodUtils.getAccessibleMethod(bean.getClass(), methodName, new Class<?>[0]);
	
	if (factoryMethod == null) {
	    throw new RuntimeException(
		    MessageFormat.format("The FactoryMethod ''{0}'' cannot be found in the class ''{1}''", 
			    describeMethod(itemPresentationModelClass, methodName), getBeanClassName()));
	} 
	
	Class<?> returnType = factoryMethod.getReturnType();
	if (!itemPresentationModelClass.isAssignableFrom(returnType)) {
	    throw new RuntimeException(
		    MessageFormat.format("The expected FactoryMethod return type is ''{0}[or subclass]'', but was ''{1}''",
			    itemPresentationModelClass.getName(), factoryMethod.toString()));
	}
	
	return factoryMethod;
    }
    private String getBeanClassName() {
	return bean.getClass().getName();
    }

    private String describeMethod(Class<?> returnType, String methodName) {
	return "public " + returnType.getName() + "[or subclass] " + methodName + "()";
    }
}
