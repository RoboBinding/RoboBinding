package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;
import java.util.List;

import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.property.AbstractDataSet;
import org.robobinding.property.ListDataSet;
import org.robobinding.property.TypedCursorDataSet;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetPropertyInfoImpl implements DataSetPropertyInfo {
	private final GetterElement getter;
	private final ItemPresentationModelAnnotationMirror annotation;
	private final String itemPresentationModelObjectTypeName;
	private boolean factoryMethodHasArg;
	private String factoryMethodReturnTypeName;

	public DataSetPropertyInfoImpl(GetterElement getter, 
			ItemPresentationModelAnnotationMirror annotation,
			String itemPresentationModelObjectTypeName) {
		this.getter = getter;
		this.annotation = annotation;
		this.itemPresentationModelObjectTypeName = itemPresentationModelObjectTypeName;
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
		if(returnType.isAssignableTo(List.class)) {
			return ListDataSet.class;
		} else if(returnType.isAssignableTo(TypedCursor.class)) {
			return TypedCursorDataSet.class;
		} else {
			throw new RuntimeException(MessageFormat.format("Property {0} has an unsupported dataSet type", getter));
		}
	}

	@Override
	public String itemPresentationModelTypeName() {
		return annotation.itemPresentationModelTypeName();
	}

	@Override
	public String itemViewFactoryTypeName() {
		if (annotation.hasItemViewFactory()) {
			return annotation.itemViewFactoryTypeName();
		}
		return null;
	}

	@Override
	public boolean isCreatedByFactoryMethod() {
		return annotation.hasFactoryMethod();
	}

	@Override
	public String factoryMethod() {
		return annotation.factoryMethod();
	}

	@Override
	public boolean factoryMethodHasArg() {
		return factoryMethodHasArg;
	}

	public void setFactoryMethodHasArg(boolean hasArg) {
		factoryMethodHasArg = hasArg;
	}

	@Override
	public String factoryMethodReturnTypeName() {
		return factoryMethodReturnTypeName;
	}

	public void setFactoryMethodReturnTypeName(String typeName) {
		this.factoryMethodReturnTypeName = typeName;
	}

	@Override
	public String itemPresentationModelObjectTypeName() {
		return itemPresentationModelObjectTypeName;
	}
}
