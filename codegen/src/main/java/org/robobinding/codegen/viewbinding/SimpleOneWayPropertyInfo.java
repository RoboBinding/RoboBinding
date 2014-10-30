package org.robobinding.codegen.viewbinding;

import org.apache.commons.lang3.StringUtils;

import com.google.common.primitives.Primitives;
import com.sun.codemodel.JDefinedClass;



/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SimpleOneWayPropertyInfo {
	private final Class<?> propertyType;
	private final String propertyName;
	
	private JDefinedClass attributeType;
	public SimpleOneWayPropertyInfo(Class<?> propertyType, String propertyName) {
		this.propertyType = propertyType;
		this.propertyName = propertyName;
	}

	public String getPropertySetter() {
		return PropertyUtils.setterNameOf(propertyName);
	}

	public Class<?> getPropertyType() {
		return Primitives.wrap(propertyType);
	}

	public String getAttributeTypeName() {
		return StringUtils.capitalize(propertyName)+"Attribute";
	}

	public String getPropertyName() {
		return propertyName;
	}
	
	public JDefinedClass getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(JDefinedClass attributeType) {
		this.attributeType = attributeType;
	}
}
