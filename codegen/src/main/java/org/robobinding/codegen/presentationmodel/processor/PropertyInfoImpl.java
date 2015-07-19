package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.apt.element.GetterElement;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.type.WrappedPrimitiveType;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;
import org.robobinding.codegen.presentationmodel.PropertyInfo;





/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfoImpl implements PropertyInfo {
	private final String name;
	private GetterElement getter;
	private SetterElement setter;
	
	public PropertyInfoImpl(String name, GetterElement getter, SetterElement setter) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
	}

	public String name() {
		return name;
	}
	
	public boolean isWritable() {
		return setter != null;
	}
	
	public boolean isReadable() {
		return getter != null;
	}
	
	public String typeName() {
		WrappedTypeMirror type = null;
		if(getter != null) {
			type = getter.returnType();
		} else {
			type = setter.parameterType();
		}
		
		if(type.isPrimitive()) {
			return ((WrappedPrimitiveType)type).boxedClassName();
		} else {
			return type.className();
		}
	}

	public String getter() {
		return getter.methodName();
	}

	public String setter() {
		return setter.methodName();
	}
}
