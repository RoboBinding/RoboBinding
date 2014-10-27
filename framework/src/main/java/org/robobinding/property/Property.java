package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
interface Property {
	Class<?> getPropertyType();

	void checkReadWriteProperty(boolean isReadWriteProperty);
}
