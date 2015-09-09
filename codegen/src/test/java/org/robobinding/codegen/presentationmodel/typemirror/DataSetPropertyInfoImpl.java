package org.robobinding.codegen.presentationmodel.typemirror;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.robobinding.codegen.presentationmodel.DataSetPropertyInfo;
import org.robobinding.itempresentationmodel.ItemViewFactory;
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
	private final PropertyDescriptor descriptor;
	private final ItemPresentationModel itemPresentationModelAnnotation;
	private final String itemPresentationModelObjectTypeName;
	private boolean factoryMethodHasArg;
	private String factoryMethodReturnTypeName;
	
	public DataSetPropertyInfoImpl(PropertyDescriptor descriptor, ItemPresentationModel itemPresentationModelAnnotation,
			String itemPresentationModelObjectTypeName) {
		this.descriptor = descriptor;
		this.itemPresentationModelAnnotation = itemPresentationModelAnnotation;
		this.itemPresentationModelObjectTypeName = itemPresentationModelObjectTypeName;
	}

	@Override
	public String name() {
		return descriptor.getName();
	}
	
	@Override
	public String type() {
		return descriptor.getPropertyType().getName();
	}

	@Override
	public String getter() {
		return descriptor.getReadMethodName();
	}

	@Override
	public Class<? extends AbstractDataSet> dataSetImplementationType() {
		if(List.class.isAssignableFrom(descriptor.getPropertyType())){
			return ListDataSet.class;
		} else if(TypedCursor.class.isAssignableFrom(descriptor.getPropertyType())) {
			return TypedCursorDataSet.class;
		} else {
			throw new RuntimeException(MessageFormat.format("Property {0} has an unsupported dataSet type", descriptor));
		}
	}

	@Override
	public String itemPresentationModelTypeName() {
		return itemPresentationModelAnnotation.value().getName();
	}

	@Override
	public String itemViewFactoryTypeName() {
		if (itemPresentationModelAnnotation.viewFactory().isAssignableFrom(ItemViewFactory.Default.class)) {
			return null;
		}
		return itemPresentationModelAnnotation.viewFactory().getName();
	}

	@Override
	public boolean isCreatedByFactoryMethod() {
		return !StringUtils.isBlank(itemPresentationModelAnnotation.factoryMethod());
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
	public String factoryMethod() {
		return StringUtils.trim(itemPresentationModelAnnotation.factoryMethod());
	}

	@Override
	public String itemPresentationModelObjectTypeName() {
		return itemPresentationModelObjectTypeName;
	}
}
