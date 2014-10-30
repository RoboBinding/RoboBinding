package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.typewrapper.MethodElementWrapper;
import org.robobinding.codegen.typewrapper.AbstractTypeElementWrapper;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfoBuilder {
	private final String propertyName;
	private MethodElementWrapper getter;
	private MethodElementWrapper setter;

	public PropertyInfoBuilder(String propertyName) {
		this.propertyName = propertyName;
	}

	public PropertyInfoBuilder setGetter(MethodElementWrapper getter) {
		this.getter = getter;
		return this;
	}

	public PropertyInfoBuilder setSetter(MethodElementWrapper setter) {
		this.setter = setter;
		return this;
	}

	public boolean isGetterSetterTypeInconsistent() {
		if((getter != null) && (setter != null)) {
			AbstractTypeElementWrapper returnType = getter.getReturnType();
			return !returnType.isSameType(setter.firstParameterType());
		}
		return false;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public PropertyInfoImpl build() {
		return new PropertyInfoImpl(propertyName, getter, setter);
	}

}
