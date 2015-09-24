package org.robobinding.codegen.apt.type;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.UnionType;
import javax.lang.model.type.UnknownTypeException;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TypeMirrorWrapper {
	private final Types types;
	private final Elements elements;
	private final WrapperVisitorImpl visitor;

	public TypeMirrorWrapper(Types types, Elements elements) {
		this.types = types;
		this.elements = elements;
		this.visitor = new WrapperVisitorImpl();
	}

	@SuppressWarnings("unchecked")
	public <T extends WrappedTypeMirror> T wrap(TypeMirror typeMirror) {
		if (typeMirror == null) {
			return null;
		}

		return (T) typeMirror.accept(visitor, null);
	}

	private class WrapperVisitorImpl implements TypeVisitor<WrappedTypeMirror, Void> {
		@Override
		public WrappedDeclaredType visitDeclared(DeclaredType t, Void p) {
			return new WrappedDeclaredType(t, types, elements);
		}

		@Override
		public WrappedPrimitiveType visitPrimitive(PrimitiveType t, Void p) {
			return new WrappedPrimitiveType(t, types, TypeMirrorWrapper.this);
		}

		@Override
		public WrappedTypeMirror visitArray(ArrayType t, Void p) {
			return new WrappedArrayType(t, types, TypeMirrorWrapper.this);
		}

		@Override
		public WrappedTypeMirror visitNoType(NoType t, Void p) {
			if (t.getKind().equals(TypeKind.VOID)) {
				return new WrappedVoidType(t, types);
			} else {
				return throwsUnsupportedOperationException();
			}
		}

		@Override
		public WrappedTypeMirror visit(TypeMirror t, Void p) {
			return throwsUnsupportedOperationException();
		}

		private WrappedTypeMirror throwsUnsupportedOperationException() {
			throw new UnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visit(TypeMirror t) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visitNull(NullType t, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visitError(ErrorType t, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visitTypeVariable(TypeVariable t, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visitWildcard(WildcardType t, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visitExecutable(ExecutableType t, Void p) {
			return throwsUnsupportedOperationException();
		}

		@Override
		public WrappedTypeMirror visitUnknown(TypeMirror t, Void p) {
			throw new UnknownTypeException(t, p);
		}

		@Override
		public WrappedTypeMirror visitUnion(UnionType t, Void p) {
			return visitUnknown(t, p);
		}

	}
}
