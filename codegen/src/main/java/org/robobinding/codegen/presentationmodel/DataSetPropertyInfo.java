package org.robobinding.codegen.presentationmodel;

import org.robobinding.annotation.PreInitializingViews;
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

	String factoryMethod();

	String itemPresentationModelObjectTypeName();

	boolean isCreatedByFactoryMethodWithArg();

	boolean isCreatedByFactoryMethodWithoutArg();

	boolean hasViewTypeSelector();

	boolean viewTypeSelectorAcceptsArg();

	String viewTypeSelector();
	
	PreInitializingViews preInitializingViews();
}
