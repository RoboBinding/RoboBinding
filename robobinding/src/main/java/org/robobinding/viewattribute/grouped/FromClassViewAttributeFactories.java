package org.robobinding.viewattribute.grouped;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FromClassViewAttributeFactories {

    public static <T extends View> PropertyViewAttributeFactory<T> propertyViewAttributeFactoryForClass(
	    final Class<? extends PropertyViewAttribute<T, ?>> propertyViewAttributeClass) {
	return new PropertyViewAttributeFactory<T>() {
	    @Override
	    public PropertyViewAttribute<T, ?> create() {
	        return newViewAttribute(propertyViewAttributeClass);
	    }
	};
    }

    public static <T extends View> MultiTypePropertyViewAttributeFactory<T> multiTypePropertyViewAttributeFactoryForClass(
	    final Class<? extends MultiTypePropertyViewAttribute<T>> multiTypePropertyViewAttributeClass) {
	return new MultiTypePropertyViewAttributeFactory<T>() {
	    @Override
	    public MultiTypePropertyViewAttribute<T> create() {
	        return newViewAttribute(multiTypePropertyViewAttributeClass);
	    }
	};
    }

    public static <T extends View> EventViewAttributeFactory<T> eventViewAttributeFactoryForClass(
	    final Class<? extends EventViewAttribute<T>> eventViewAttributeClass) {
	return new EventViewAttributeFactory<T>() {
	    @Override
	    public EventViewAttribute<T> create() {
	        return newViewAttribute(eventViewAttributeClass);
	    }
	};
    }

    public static <T extends View> GroupedViewAttributeFactory<T> groupedViewAttributeFactoryForClass(
	    final Class<? extends GroupedViewAttribute<T>> groupedViewAttributeClass) {
	return new GroupedViewAttributeFactory<T>() {
	    @Override
	    public GroupedViewAttribute<T> create() {
	        return newViewAttribute(groupedViewAttributeClass);
	    }
	};
    }

    static <T extends Object> T newViewAttribute(Class<T> viewAttributeClass) {
	try {
	    return viewAttributeClass.newInstance();
	} catch (InstantiationException e) {
	    throw new RuntimeException("View Attribute class " + viewAttributeClass.getName() + " could not be instantiated: " + e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("View Attribute class " + viewAttributeClass.getName() + " is not public");
	}
    }

    private FromClassViewAttributeFactories() {
    }
}
