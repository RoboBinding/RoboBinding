package org.robobinding.codegen.processor;

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
public class TypeElementWrapper {
	private final ProcessingContext context;
	private final Types types;
	private final TypeElement type;

	public TypeElementWrapper(ProcessingContext context, Types types, TypeElement type) {
		this.context = context;
		this.types = types;
		this.type = type;
	}

	public boolean isObjectType() {
		return types.isSameType(type.asType(), context.typeMirrorOf(Object.class));
	}

	public List<MethodElementWrapper> getMethods() {
		List<ExecutableElement> methods = ElementFilter.methodsIn(type.getEnclosedElements());
		
		List<MethodElementWrapper> result = Lists.newArrayList();
		for(ExecutableElement method : methods) {
			result.add(context.wrapper(method));
		}
		return result;
	}

	public TypeElementWrapper getSuperclass() {
		return context.typeElementOf(type.getSuperclass());
	}

	public String typeName() {
		return type.getQualifiedName().toString();
	}
	
	public boolean isAssignableTo(TypeMirror anotherType) {
		return types.isAssignable(type.asType(), anotherType);
	}

	public boolean isAssignableTo(Class<?> anotherType) {
		return isAssignableTo(context.typeMirrorOf(anotherType));
	}
	
	public boolean isNotConcreteClass() {
		return !type.getKind().isClass() || type.getModifiers().contains(Modifier.ABSTRACT);
	}

}
