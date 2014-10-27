package org.robobinding.viewattribute.grouped;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class FromClassViewAttributeFactories {

	public static <ViewType> PropertyViewAttributeFactory<ViewType> propertyViewAttributeFactoryForClass(
			final Class<? extends PropertyViewAttribute<ViewType, ?>> propertyViewAttributeClass) {
		return new PropertyViewAttributeFactory<ViewType>() {
			@Override
			public PropertyViewAttribute<ViewType, ?> create() {
				return newViewAttribute(propertyViewAttributeClass);
			}
		};
	}

	public static <ViewType> MultiTypePropertyViewAttributeFactory<ViewType> multiTypePropertyViewAttributeFactoryForClass(
			final Class<? extends MultiTypePropertyViewAttribute<ViewType>> multiTypePropertyViewAttributeClass) {
		return new MultiTypePropertyViewAttributeFactory<ViewType>() {
			@Override
			public MultiTypePropertyViewAttribute<ViewType> create() {
				return newViewAttribute(multiTypePropertyViewAttributeClass);
			}
		};
	}

	public static <ViewType> EventViewAttributeFactory<ViewType> eventViewAttributeFactoryForClass(
			final Class<? extends EventViewAttribute<ViewType>> eventViewAttributeClass) {
		return new EventViewAttributeFactory<ViewType>() {
			@Override
			public EventViewAttribute<ViewType> create() {
				return newViewAttribute(eventViewAttributeClass);
			}
		};
	}

	public static <ViewType> GroupedViewAttributeFactory<ViewType> groupedViewAttributeFactoryForClass(
			final Class<? extends GroupedViewAttribute<ViewType>> groupedViewAttributeClass) {
		return new GroupedViewAttributeFactory<ViewType>() {
			@Override
			public GroupedViewAttribute<ViewType> create() {
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
