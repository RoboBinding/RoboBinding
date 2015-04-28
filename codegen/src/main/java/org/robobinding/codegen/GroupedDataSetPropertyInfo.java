package org.robobinding.codegen;

import org.robobinding.property.AbstractGroupedDataSet;


/**
 * @since 1.0
 * @author Jihun Lee
 *
 */
public interface GroupedDataSetPropertyInfo {

	String name();
	
	String type();

	String getter();

	Class<? extends AbstractGroupedDataSet> groupedDataSetImplementationType();

	String groupedItemPresentationModelTypeName();

	boolean isCreatedByFactoryMethod();

	String factoryMethod();

	String groupedItemPresentationModelObjectTypeName();
}
