package org.robobinding.codegen.apt.type;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.robobinding.util.Objects;

public abstract class WrappedTypeMirror {
	private final TypeMirror type;
	private final Types types;

	public WrappedTypeMirror(TypeMirror type, Types types) {
		this.type = type;
		this.types = types;
	}

	public boolean isArray() {
		return false;
	}
	
	public boolean isDeclaredType() {
		return false;
	}

	public boolean isPrimitive() {
		return false;
	}

	public boolean isVoid() {
		return false;
	}
	
	public boolean isBoolean() {
		return false;
	}

	public abstract String className();
	
	public abstract boolean isOfType(Class<?> type);

	public boolean isAssignableTo(Class<?> type) {
		return isAssignableTo(type.getName());
	}

	public abstract boolean isAssignableTo(String typeName);
	
	protected boolean sameErasedTypeAs(TypeMirror type) {
		return types.isSameType(types.erasure(this.type), types.erasure(type));
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof WrappedTypeMirror))
			return false;
	
		final WrappedTypeMirror that = (WrappedTypeMirror) other;
		return sameErasedTypeAs(that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(types.erasure(type));
	}

	public String toString() {
		return type.toString();
	}
}
