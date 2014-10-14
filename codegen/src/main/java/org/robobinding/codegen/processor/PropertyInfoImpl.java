package org.robobinding.codegen.processor;

import org.robobinding.codegen.PropertyInfo;





/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfoImpl implements PropertyInfo {
	private final String name;
	private final MethodElementWrapper getter;
	private final MethodElementWrapper setter;
	
	public PropertyInfoImpl(String name, MethodElementWrapper getter, MethodElementWrapper setter) {
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
		if(getter != null) {
			return getter.nonPrimitiveReturnTypeName();
		} else {
			return setter.firstParameterTypeName();
		}
	}

	public String getter() {
		return getter.methodName();
	}

	public String setter() {
		return setter.methodName();
	}
}
