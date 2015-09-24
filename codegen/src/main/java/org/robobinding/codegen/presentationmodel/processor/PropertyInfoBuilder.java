package org.robobinding.codegen.presentationmodel.processor;

import java.text.MessageFormat;

import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfoBuilder {
	private final String propertyName;
	private GetterElement getter;
	private SetterElement setter;

	public PropertyInfoBuilder(String propertyName) {
		this.propertyName = propertyName;
	}

	public PropertyInfoBuilder setGetter(GetterElement getter) {
		this.getter = getter;
		return this;
	}

	public PropertyInfoBuilder setSetter(SetterElement setter) {
		this.setter = setter;
		return this;
	}

	public void validateGetterSetterTypeConsistency() {
		if(isGetterSetterTypeInconsistent()) {
			throw new RuntimeException(MessageFormat.format("The property ''{0}'' has an inconsistent type in getter and setter",
					propertyName));
		}
	}

	private boolean isGetterSetterTypeInconsistent() {
		if((getter != null) && (setter != null)) {
			WrappedTypeMirror returnType = getter.returnType();
			return !returnType.equals(setter.parameterType());
		}
		
		return false;
	}

	public PropertyInfoImpl build() {
		return new PropertyInfoImpl(propertyName, getter, setter);
	}
}
