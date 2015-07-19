package org.robobinding.codegen.viewbinding;

import org.apache.commons.lang3.StringUtils;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.type.WrappedPrimitiveType;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;

import com.google.common.base.Objects;
import com.sun.codemodel.JDefinedClass;



/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SimpleOneWayPropertyInfo {
	private final SetterElement setter;
	
	private JDefinedClass bindingType;
	public SimpleOneWayPropertyInfo(SetterElement setter) {
		this.setter = setter;
	}

	public String propertySetter() {
		return setter.methodName();
	}

	public String propertyType() {
		WrappedTypeMirror parameterType = setter.parameterType();
		if(parameterType.isPrimitive()) {
			return ((WrappedPrimitiveType)parameterType).boxedClassName();
		} else {
			return parameterType.className();
		}
	}

	public String propertyName() {
		return setter.propertyName();
	}
	
	public JDefinedClass getBindingClass() {
		return bindingType;
	}
	
	public JDefinedClass defineBindingClass(ClassDefinitionCallback callback) {
		bindingType = callback.define(bindingTypeName());
		return bindingType;
	}

	String bindingTypeName() {
		return StringUtils.capitalize(propertyName())+"Attribute";
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof SimpleOneWayPropertyInfo))
			return false;

		final SimpleOneWayPropertyInfo that = (SimpleOneWayPropertyInfo) other;
		return Objects.equal(setter, that.setter)
				&& Objects.equal(bindingType, that.bindingType);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(setter)
				+ Objects.hashCode(bindingType);
	}
}
