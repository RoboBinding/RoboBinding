package org.robobinding.codegen.apt.element;

import java.util.Collections;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;

import org.robobinding.codegen.apt.MessagerLoggerFactory;
import org.robobinding.codegen.apt.MethodElementFilter;
import org.robobinding.codegen.apt.SetterElementFilter;
import org.robobinding.codegen.apt.type.TypeMirrorWrapper;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class WrappedTypeElement extends AbstractWrappedElement {
	private final TypeElement element;
	private final WrappedDeclaredType type;
	private final ElementWrapper wrapper;
	private final Types types;
	private final TypeMirrorWrapper typeWrapper;
	private final MessagerLoggerFactory loggerFactory;
	
	public WrappedTypeElement(TypeElement element, WrappedDeclaredType type, TypeMirrorWrapper typeWrapper, 
			MessagerLoggerFactory loggerFactory, ElementWrapper wrapper, Types types) {
		super(element, typeWrapper, loggerFactory);
		
		this.element = element;
		this.type = type;
		this.wrapper = wrapper;
		this.types = types;
		this.typeWrapper = typeWrapper;
		this.loggerFactory = loggerFactory;
	}
	
	public List<MethodElement> methodsRecursively(MethodElementFilter filter) {
		return methodsRecursively(filter, this);
	}
	
	private List<MethodElement> methodsRecursively(MethodElementFilter filter, WrappedTypeElement fromTypeElement) {
		if(fromTypeElement.isOfType(Object.class)) {
			return Collections.emptyList();
		}
		
		List<ExecutableElement> methods = ElementFilter.methodsIn(fromTypeElement.element.getEnclosedElements());

		List<MethodElement> result = Lists.newArrayList();
		for (ExecutableElement method : methods) {
			MethodElement methodElement = wrapper.wrap(method);
			if(filter.include(methodElement)) {
				result.add(methodElement);
			}
		}
		
		result.addAll(methodsRecursively(filter, fromTypeElement.superclass()));
		
		return result;
	}	
	public List<SetterElement> looseSetters(SetterElementFilter filter) {
		List<ExecutableElement> methods = ElementFilter.methodsIn(element.getEnclosedElements());

		List<SetterElement> result = Lists.newArrayList();
		for (ExecutableElement method : methods) {
			MethodElement methodElement = wrapper.wrap(method);
			if(!methodElement.isLooseSetter()) {
				continue;
			}
			
			SetterElement looseSetter = methodElement.asLooseSetter();
			if(filter.include(looseSetter)) {
				result.add(looseSetter);
			}
		}
		return result;
	}

	private WrappedTypeElement superclass() {
		return typeElementOf((DeclaredType)element.getSuperclass());
	}
	
	private WrappedTypeElement typeElementOf(DeclaredType declaredType) {
		WrappedDeclaredType type = typeWrapper.wrap(declaredType);
		TypeElement element = (TypeElement)types.asElement(declaredType);
		return new WrappedTypeElement(element, type, typeWrapper, loggerFactory, wrapper, types);
	}

	public WrappedTypeElement findDirectSuperclassOf(Class<?> type) {
		WrappedTypeElement superclass = superclass();
		if(superclass.isOfType(type)) {
			return superclass;
		} else {
			return null;
		}
	}

	public String qName() {
		return element.getQualifiedName().toString();
	}

	public boolean isAssignableTo(Class<?> type) {
		return this.type.isAssignableTo(type);
	}

	public boolean isOfType(Class<?> type) {
		return this.type.isOfType(type);
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof WrappedTypeElement))
			return false;
	
		WrappedTypeElement that = (WrappedTypeElement)other;
		return type.equals(that.type);
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}

	public boolean isNotConcreteClass() {
		return !isConcreteClass();
	}

	public boolean isConcreteClass() {
		return element.getKind().isClass() && (!element.getModifiers().contains(Modifier.ABSTRACT));
	}

	public WrappedTypeElement firstTypeArgument() {
		List<? extends TypeMirror> typeArguments = type.getTypeArguments();
		DeclaredType firstTypeArgument = (DeclaredType)typeArguments.get(0);
		return typeElementOf(firstTypeArgument);
	}
}
