package org.robobinding.codegen.processor;

import java.text.MessageFormat;
import java.util.List;

import org.robobinding.codegen.DataSetPropertyInfo;
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
		return getter.returnTypeName();
	}

	@Override
	public String getter() {
		return getter.methodName();
	}

	@Override
	public Class<? extends AbstractDataSet> dataSetImplementationType() {
		if(getter.isReturnTypeAssignableTo(List.class)) {
			return ListDataSet.class;
		} else if(getter.isReturnTypeAssignableTo(type())) {
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
