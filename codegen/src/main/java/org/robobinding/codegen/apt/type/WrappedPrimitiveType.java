package org.robobinding.codegen.apt.type;

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;

public class WrappedPrimitiveType extends WrappedTypeMirror {
	private final PrimitiveType type;
	private final Types types;
	private final TypeMirrorWrapper wrapper;
	public WrappedPrimitiveType(PrimitiveType type, Types types, TypeMirrorWrapper wrapper) {
		super(type, types);
		
		this.type = type;
		this.types = types;
		this.wrapper = wrapper;
	}
	
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public boolean isBoolean() {
		return type.getKind().equals(TypeKind.BOOLEAN);
	}

	@Override
	public String className() {
		return keyword();
	}
	
	private String keyword() {
		return String.valueOf(type.getKind()).toLowerCase();
	}

	public String boxedClassName() {
		WrappedDeclaredType boxed = wrapper.wrap(types.boxedClass(type).asType());
		return boxed.className();
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
