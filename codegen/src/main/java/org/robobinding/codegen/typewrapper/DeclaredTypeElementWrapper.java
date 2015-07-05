package org.robobinding.codegen.typewrapper;

import java.lang.reflect.Modifier;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DeclaredTypeElementWrapper extends AbstractTypeElementWrapper {
	private final TypeElement element;
	private final DeclaredType type;

	public DeclaredTypeElementWrapper(ProcessingContext context, TypesWrapper types, TypeElement element, DeclaredType type) {
		super(context, types, element);
		
		this.element = element;
		this.type = type;
	}

	@Override
	public boolean isObjectRoot() {
		return isOfType(Object.class);
	}
	
	private boolean isOfType(Class<?> anotherType) {
		return types.isSameType(element.asType(), anotherType);
	}

	@Override
	public List<MethodElementWrapper> getMethods() {
		List<ExecutableElement> methods = ElementFilter.methodsIn(element.getEnclosedElements());
		
		List<MethodElementWrapper> result = Lists.newArrayList();
		for(ExecutableElement method : methods) {
			result.add(context.wrapper(method));
		}
		return result;
	}

	@Override
	public AbstractTypeElementWrapper getSuperclass() {
		return context.typeElementOf(element.getSuperclass());
	}
	
	@Override
	public String typeName() {
		return element.getQualifiedName().toString();
	}

	@Override
	public boolean isConcreteClass() {
		return element.getKind().isClass() && (!element.getModifiers().contains(Modifier.ABSTRACT));
	}

	@Override
	protected DeclaredType asType() {
		return type;
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
	
	public DeclaredTypeElementWrapper findDirectSuperclassOf(Class<?> superclass) {
		DeclaredType superType = (DeclaredType)element.getSuperclass();
		if(types.isSameType(superclass, superType)) {
			return context.declaredTypeElementOf(superType);
		} else {
			return null;
		}
	}
	
	public String getFirstTypeArgumentClassName() {
		TypeMirror firstTypeArgument = type.getTypeArguments().get(0);
		DeclaredTypeElementWrapper typeElement = context.declaredTypeElementOf((DeclaredType)firstTypeArgument);
		return typeElement.typeName();
	}
}
