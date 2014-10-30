package org.robobinding.codegen.typewrapper;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NonDeclaredTypeElementWrapper extends AbstractTypeElementWrapper {
	private final TypeMirror type;
	
	public NonDeclaredTypeElementWrapper(ProcessingContext context, Types types, TypeMirror type) {
		super(context, types, new NullElement());
		
		this.type = type;
	}
	
	@Override
	public boolean isVoid() {
		return type.getKind().equals(TypeKind.VOID);
	}
	
	public boolean isBoolean() {
		return type.getKind().equals(TypeKind.BOOLEAN);
	}
	
	@Override
	public boolean isObjectRoot() {
		return false;
	}

	@Override
	public String typeName() {
		if(type instanceof ArrayType) {
			AbstractTypeElementWrapper componentType = context.typeElementOf(((ArrayType)type).getComponentType());
			return componentType.typeName()+"[]";
		} else if(isPrimitive()) {
			return String.valueOf(type.getKind()).toLowerCase();
		} else {
			throw new RuntimeException("Type name cannot be identified as it is neither an array nor a primitive.");
		}
	}
	
	private boolean isPrimitive() {
		return type.getKind().isPrimitive();
	}
	
	@Override
	public boolean isNotPrimitive() {
		return !isPrimitive();
	}

	@Override
	public String nonPrimitiveTypeName() {
		if(isPrimitive()) {
			return context.wrapper(types.boxedClass((PrimitiveType)type)).typeName();
		} else {
			return typeName();
		}
	}

	@Override
	public List<MethodElementWrapper> getMethods() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AbstractTypeElementWrapper getSuperclass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isConcreteClass() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected TypeMirror asType() {
		return type;
	}
	
	private static class NullElement implements Element {

		@Override
		public TypeMirror asType() {
			throw new UnsupportedOperationException();
		}

		@Override
		public ElementKind getKind() {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<? extends AnnotationMirror> getAnnotationMirrors() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<Modifier> getModifiers() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Name getSimpleName() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Element getEnclosingElement() {
			throw new UnsupportedOperationException();
		}

		@Override
		public List<? extends Element> getEnclosedElements() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <R, P> R accept(ElementVisitor<R, P> v, P p) {
			throw new UnsupportedOperationException();
		}
		
	}

}
