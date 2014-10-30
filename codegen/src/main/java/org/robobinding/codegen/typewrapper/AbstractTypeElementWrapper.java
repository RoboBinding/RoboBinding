package org.robobinding.codegen.typewrapper;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractTypeElementWrapper extends AbstractElementWrapper {
	public AbstractTypeElementWrapper(ProcessingContext context, Types types, Element element) {
		super(context, types, element);
	}
	
	public boolean isAssignableTo(TypeMirror anotherType) {
		return types.isAssignable(asType(), anotherType);
	}

	public boolean isAssignableTo(Class<?> anotherType) {
		return isAssignableTo(context.typeMirrorOf(anotherType));
	}

	public boolean isAssignableTo(String anotherType) {
		return isAssignableTo(context.typeMirrorOf(anotherType));
	}

	public boolean isNotAssignableTo(String anotherType) {
		return !isAssignableTo(anotherType);
	}

	public boolean isSameType(AbstractTypeElementWrapper anotherType) {
		return types.isSameType(asType(), anotherType.asType());
	}

	protected abstract TypeMirror asType();

	public abstract String typeName();

	public abstract List<MethodElementWrapper> getMethods();

	public abstract AbstractTypeElementWrapper getSuperclass();

	public abstract String nonPrimitiveTypeName();

	public abstract boolean isConcreteClass();
	public boolean isNotConcreteClass() {
		return !isConcreteClass();
	}

	public abstract boolean isObjectRoot();

	public abstract boolean isVoid();

	public abstract boolean isBoolean();

	public abstract boolean isNotPrimitive();

}
