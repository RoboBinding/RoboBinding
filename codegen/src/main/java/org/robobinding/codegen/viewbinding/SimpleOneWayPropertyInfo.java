package org.robobinding.codegen.viewbinding;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Objects;
import com.google.common.primitives.Primitives;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;



/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SimpleOneWayPropertyInfo {
	private final Class<?> propertyType;
	private final String propertyName;
	
	private JDefinedClass bindingType;
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

	public String getPropertyName() {
		return propertyName;
	}
	
	public JDefinedClass getBindingClass() {
		return bindingType;
	}
	
	public JDefinedClass defineBindingClass(ClassDefinitionCallback callback) throws JClassAlreadyExistsException {
		bindingType = callback.define(bindingTypeName());
		return bindingType;
	}

	String bindingTypeName() {
		return StringUtils.capitalize(propertyName)+"Attribute";
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof SimpleOneWayPropertyInfo))
			return false;

		final SimpleOneWayPropertyInfo that = (SimpleOneWayPropertyInfo) other;
		return Objects.equal(propertyType, that.propertyType)
				&& Objects.equal(propertyName, that.propertyName)
				&& Objects.equal(bindingType, that.bindingType);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(propertyType)
				+ Objects.hashCode(propertyName)
				+ Objects.hashCode(bindingType);
	}
}
