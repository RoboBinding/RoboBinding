package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface Properties {
	Class<?> getPropertyType(String propertyName);

	<T> ValueModel<T> getReadWriteProperty(String propertyName);

	<T> ValueModel<T> getReadOnlyProperty(String propertyName);

	DataSetValueModel getDataSetProperty(String propertyName);
}
