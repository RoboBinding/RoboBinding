package org.robobinding.viewattribute.grouped;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class FromClassViewAttributeFactories {

	public static <ViewType> OneWayPropertyViewAttributeFactory<ViewType> oneWayPropertyViewAttributeFactoryForClass(
			final Class<? extends OneWayPropertyViewAttribute<ViewType, ?>> propertyViewAttributeClass) {
		return new OneWayPropertyViewAttributeFactory<ViewType>() {
			@Override
			public OneWayPropertyViewAttribute<ViewType, ?> create() {
				return newViewAttribute(propertyViewAttributeClass);
			}
		};
	}

	public static <ViewType> TwoWayPropertyViewAttributeFactory<ViewType> twoWayPropertyViewAttributeFactoryForClass(
			final Class<? extends TwoWayPropertyViewAttribute<ViewType, ?, ?>> propertyViewAttributeClass) {
		return new TwoWayPropertyViewAttributeFactory<ViewType>() {
			@Override
			public TwoWayPropertyViewAttribute<ViewType, ?, ?> create() {
				return newViewAttribute(propertyViewAttributeClass);
			}
		};
	}

	public static <ViewType> OneWayMultiTypePropertyViewAttributeFactory<ViewType> oneWayMultiTypePropertyViewAttributeFactoryForClass(
			final Class<? extends OneWayMultiTypePropertyViewAttribute<ViewType>> multiTypePropertyViewAttributeClass) {
		return new OneWayMultiTypePropertyViewAttributeFactory<ViewType>() {
			@Override
			public OneWayMultiTypePropertyViewAttribute<ViewType> create() {
				return newViewAttribute(multiTypePropertyViewAttributeClass);
			}
		};
	}

	public static <ViewType> TwoWayMultiTypePropertyViewAttributeFactory<ViewType> twoWayMultiTypePropertyViewAttributeFactoryForClass(
			final Class<? extends TwoWayMultiTypePropertyViewAttribute<ViewType>> multiTypePropertyViewAttributeClass) {
		return new TwoWayMultiTypePropertyViewAttributeFactory<ViewType>() {
			@Override
			public TwoWayMultiTypePropertyViewAttribute<ViewType> create() {
				return newViewAttribute(multiTypePropertyViewAttributeClass);
			}
		};
	}

	public static <ViewType> EventViewAttributeFactory<ViewType> eventViewAttributeFactoryForClass(
			final Class<? extends EventViewAttribute<ViewType, ? extends ViewAddOn>> eventViewAttributeClass) {
		return new EventViewAttributeFactory<ViewType>() {
			@Override
			public EventViewAttribute<ViewType, ? extends ViewAddOn> create() {
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
