package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;
import java.util.List;

import org.robobinding.annotation.PreInitializingViews;
import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.itempresentationmodel.DataSetObservable;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.property.AbstractDataSet;
import org.robobinding.property.ListDataSet;
import org.robobinding.property.ObservableDataSet;
import org.robobinding.property.TypedCursorDataSet;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetPropertyInfoImpl implements DataSetPropertyInfo {
	private final GetterElement getter;
	private final String itemPresentationModelTypeName;
	private final String itemPresentationModelObjectTypeName;
	private final MethodElement factoryMethod;
	private final MethodElement viewTypeSelector;
	private final PreInitializingViews preInitializingViews;
	
	public DataSetPropertyInfoImpl(GetterElement getter, 
			String itemPresentationModelTypeName,
			String itemPresentationModelObjectTypeName,
			MethodElement factoryMethod, 
			MethodElement viewTypeSelector,
			PreInitializingViews preInitializingViews) {
		this.getter = getter;
		this.itemPresentationModelTypeName = itemPresentationModelTypeName;
		this.itemPresentationModelObjectTypeName = itemPresentationModelObjectTypeName;
		this.factoryMethod = factoryMethod;
		this.viewTypeSelector = viewTypeSelector;
		this.preInitializingViews = preInitializingViews;
	}

	@Override
	public String name() {
		return getter.propertyName();
	}
	
	@Override
	public String type() {
		return getter.returnType().className();
	}

	@Override
	public String getter() {
		return getter.methodName();
	}

	@Override
	public Class<? extends AbstractDataSet> dataSetImplementationType() {
		WrappedDeclaredType returnType = getter.returnType();
		if(returnType.isAssignableTo(DataSetObservable.class)) {
			return ObservableDataSet.class;
		} else if(returnType.isAssignableTo(List.class)) {
			return ListDataSet.class;
		} else if(returnType.isAssignableTo(TypedCursor.class)) {
			return TypedCursorDataSet.class;
		} else {
			throw new RuntimeException(MessageFormat.format("Property {0} has an unsupported dataSet type", getter));
		}
	}

	@Override
	public String itemPresentationModelTypeName() {
		return itemPresentationModelTypeName;
	}

	@Override
	public String factoryMethod() {
		return factoryMethod.methodName();
	}

	@Override
	public String itemPresentationModelObjectTypeName() {
		return itemPresentationModelObjectTypeName;
	}

	private boolean hasFactoryMethod() {
		return factoryMethod != null;
	}

	@Override
	public boolean isCreatedByFactoryMethodWithArg() {
		return hasFactoryMethod() && factoryMethod.hasParameter();
	}

	@Override
	public boolean isCreatedByFactoryMethodWithoutArg() {
		return hasFactoryMethod() && factoryMethod.hasNoParameter();
	}

	@Override
	public boolean hasViewTypeSelector() {
		return viewTypeSelector != null;
	}

	@Override
	public boolean viewTypeSelectorAcceptsArg() {
		return viewTypeSelector.hasParameter();
	}

	@Override
	public String viewTypeSelector() {
		return viewTypeSelector.methodName();
	}
	
	@Override
	public PreInitializingViews preInitializingViews() {
		return preInitializingViews;
	}
}
