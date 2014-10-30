package org.robobinding.codegen.typewrapper;

import java.lang.reflect.Modifier;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DeclaredTypeElementWrapper extends AbstractTypeElementWrapper {
	private final TypeElement type;

	public DeclaredTypeElementWrapper(ProcessingContext context, Types types, TypeElement type) {
		super(context, types, type);
		
		this.type = type;
	}

	@Override
	public boolean isObjectRoot() {
		return isOfType(Object.class);
	}
	
	private boolean isOfType(Class<?> anotherType) {
		return types.isSameType(type.asType(), context.typeMirrorOf(anotherType));
	}

	@Override
	public List<MethodElementWrapper> getMethods() {
		List<ExecutableElement> methods = ElementFilter.methodsIn(type.getEnclosedElements());
		
		List<MethodElementWrapper> result = Lists.newArrayList();
		for(ExecutableElement method : methods) {
			result.add(context.wrapper(method));
		}
		return result;
	}

	@Override
	public AbstractTypeElementWrapper getSuperclass() {
		return context.typeElementOf(type.getSuperclass());
	}
	
	@Override
	public String typeName() {
		return type.getQualifiedName().toString();
	}

	@Override
	public boolean isConcreteClass() {
		return type.getKind().isClass() && (!type.getModifiers().contains(Modifier.ABSTRACT));
	}

	@Override
	protected TypeMirror asType() {
		return type.asType();
	}

	@Override
	public String nonPrimitiveTypeName() {
		return typeName();
	}

	@Override
	public boolean isVoid() {
		return false;
	}

	@Override
	public boolean isBoolean() {
		return isOfType(Boolean.class);
	}

	@Override
	public boolean isNotPrimitive() {
		return true;
	}
}
