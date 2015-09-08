package org.robobinding.codegen.presentationmodel;

import org.robobinding.property.AbstractDataSet;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface DataSetPropertyInfo {

	String name();
	
	String type();

	String getter();

	Class<? extends AbstractDataSet> dataSetImplementationType();

	String itemPresentationModelTypeName();

	//boolean isCreatedByFactoryMethod();

	String factoryMethod();

	String itemPresentationModelObjectTypeName();

	boolean isCreatedByFactoryMethodWithArg();

	boolean isCreatedByFactoryMethodWithoutArg();

	boolean hasViewTypeSelector();

	boolean viewTypeSelectorAcceptsArg();

	String viewTypeSelector();
}
