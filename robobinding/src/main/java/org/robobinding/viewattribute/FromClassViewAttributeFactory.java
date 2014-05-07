package org.robobinding.viewattribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FromClassViewAttributeFactory<T extends ViewAttribute> implements ViewAttributeFactory<T> {
    private final Class<T> viewAttributeClass;

    public static <T extends ViewAttribute> ViewAttributeFactory<T> viewAttributeFactoryForClass(Class<T> viewAttributeClass) {
	return new FromClassViewAttributeFactory<T>(viewAttributeClass);
    }

    private FromClassViewAttributeFactory(Class<T> viewAttributeClass) {
	this.viewAttributeClass = viewAttributeClass;
    }

    public T create() {
	try {
	    return viewAttributeClass.newInstance();
	} catch (InstantiationException e) {
	    throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " could not be instantiated: " + e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
	}
    }
}
