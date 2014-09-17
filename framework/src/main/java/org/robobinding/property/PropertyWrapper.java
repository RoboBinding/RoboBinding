package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyWrapper implements Property {
	private final Property property;

	public PropertyWrapper(Property property) {
		this.property = property;
	}

	@Override
	public Class<?> getPropertyType() {
		return property.getPropertyType();
	}

	@Override
	public void checkReadWriteProperty(boolean isReadWriteProperty) {
		property.checkReadWriteProperty(isReadWriteProperty);
	}

}
