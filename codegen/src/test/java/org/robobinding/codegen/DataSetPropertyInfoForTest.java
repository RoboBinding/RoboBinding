package org.robobinding.codegen;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.property.AbstractDataSet;
import org.robobinding.property.ArrayDataSet;
import org.robobinding.property.ListDataSet;
import org.robobinding.property.TypedCursorDataSet;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetPropertyInfoForTest {
	private final PropertyDescriptor descriptor;
	private final ItemPresentationModel itemPresentationModelAnnotation;
	private final String itemPresentationModelObjectTypeName;
	
	public DataSetPropertyInfoForTest(PropertyDescriptor descriptor, ItemPresentationModel itemPresentationModelAnnotation,
			String itemPresentationModelObjectTypeName) {
		this.descriptor = descriptor;
		this.itemPresentationModelAnnotation = itemPresentationModelAnnotation;
		this.itemPresentationModelObjectTypeName = itemPresentationModelObjectTypeName;
	}

	public String name() {
		return descriptor.getName();
	}
	
	public Class<?> type() {
		return descriptor.getPropertyType();
	}

	public String getter() {
		return descriptor.getReadMethodName();
	}

	public Class<? extends AbstractDataSet> dataSetImplementationType() {
		if(List.class.isAssignableFrom(type())){
			return ListDataSet.class;
		} else if(type().isArray()) {
			return ArrayDataSet.class;
		} else if(TypedCursor.class.isAssignableFrom(type())) {
			return TypedCursorDataSet.class;
		} else {
			throw new RuntimeException(MessageFormat.format("Property {0} has an unsupported dataSet type", descriptor));
		}
	}

	public Class<?> itemPresentationModelType() {
		return itemPresentationModelAnnotation.value();
	}

	public boolean isCreatedByFactoryMethod() {
		return !StringUtils.isBlank(itemPresentationModelAnnotation.factoryMethod());
	}

	public String factoryMethod() {
		return StringUtils.trim(itemPresentationModelAnnotation.factoryMethod());
	}

	public String itemPresentationModelObjectTypeName() {
		return itemPresentationModelObjectTypeName;
	}
}
