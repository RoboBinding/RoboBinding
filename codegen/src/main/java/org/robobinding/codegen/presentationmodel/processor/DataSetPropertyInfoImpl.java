package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;
import java.util.List;

import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.codegen.typewrapper.MethodElementWrapper;
import org.robobinding.codegen.typewrapper.AbstractTypeElementWrapper;
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
	private final String name;
	private final MethodElementWrapper getter;
	private final ItemPresentationModelAnnotationMirror annotation;
	private final String itemPresentationModelObjectTypeName;
	
	public DataSetPropertyInfoImpl(String name, MethodElementWrapper getter, 
			ItemPresentationModelAnnotationMirror annotation,
			String itemPresentationModelObjectTypeName) {
		this.name = name;
		this.getter = getter;
		this.annotation = annotation;
		this.itemPresentationModelObjectTypeName = itemPresentationModelObjectTypeName;
	}

	@Override
	public String name() {
		return name;
	}
	
	@Override
	public String type() {
		return getter.getReturnType().typeName();
	}

	@Override
	public String getter() {
		return getter.methodName();
	}

	@Override
	public Class<? extends AbstractDataSet> dataSetImplementationType() {
		AbstractTypeElementWrapper returnType = getter.getReturnType();
		if(returnType.isAssignableTo(List.class)) {
			return ListDataSet.class;
		} else if(returnType.isAssignableTo(TypedCursor.class)) {
			return TypedCursorDataSet.class;
		} else {
			throw new RuntimeException(MessageFormat.format("Property {0} has an unsupported dataSet type", getter.toString()));
		}
	}

	@Override
	public String itemPresentationModelTypeName() {
		return annotation.itemPresentationModelTypeName();
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
	public String itemPresentationModelObjectTypeName() {
		return itemPresentationModelObjectTypeName;
	}
}
