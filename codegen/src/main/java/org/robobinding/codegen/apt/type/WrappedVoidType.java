package org.robobinding.codegen.apt.type;

import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;

public class WrappedVoidType extends WrappedTypeMirror {
	private final NoType type;

	public WrappedVoidType(NoType type, Types types) {
		super(type, types);

		this.type = type;
	}

	public boolean isVoid() {
		return type.getKind().equals(TypeKind.VOID);
	}

	public String className() {
		if (isVoid()) {
			return keyword();
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private String keyword() {
		return String.valueOf(type.getKind()).toLowerCase();
	}
}
