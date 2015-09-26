package org.robobinding.codegen.apt.type;

import javax.lang.model.type.ArrayType;
import javax.lang.model.util.Types;

public class WrappedArrayType extends WrappedTypeMirror {
	private final ArrayType type;
	private final TypeMirrorWrapper wrapper;

	public WrappedArrayType(ArrayType type, Types types, TypeMirrorWrapper wrapper) {
		super(type, types);

		this.type = type;
		this.wrapper = wrapper;
	}

	@Override
	public boolean isArray() {
		return true;
	}
	
	@Override
	public String className() {
		WrappedTypeMirror componentType = componentType();
		return componentType.className() + "[]";
	}
	
	public WrappedTypeMirror componentType() {
		return wrapper.wrap(type.getComponentType());
	}
	
	@Override
	public boolean isOfType(Class<?> type) {
		return className().equals(type.getName());
	}
	
	@Override
	public boolean isAssignableTo(String typeName) {
		return className().equals(typeName);
	}
}
