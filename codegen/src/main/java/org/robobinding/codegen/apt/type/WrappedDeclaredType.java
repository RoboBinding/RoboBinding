package org.robobinding.codegen.apt.type;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class WrappedDeclaredType extends WrappedTypeMirror {
	private final DeclaredType type;
	private final Types types;
	private final Elements elements;

	public WrappedDeclaredType(DeclaredType type, Types types, Elements elements) {
		super(type, types);
		
		this.type = type;
		this.types = types;
		this.elements = elements;
	}

	@Override
	public String className() {
		return ((TypeElement)type.asElement()).getQualifiedName().toString();
	}
	
	@Override
	public boolean isBoolean() {
		return isOfType(Boolean.class);
	}
	
	@Override
	public boolean isDeclaredType() {
		return true;
	}
	
	public List<? extends TypeMirror> getTypeArguments() {
		return type.getTypeArguments();
	}
	
	@Override
	public boolean isOfType(Class<?> type) {
		return sameErasedTypeAs(typeOf(type.getName()));
	}

	private TypeMirror typeOf(String typeName) {
		return elements.getTypeElement(typeName).asType();
	}

	@Override
	public boolean isAssignableTo(String typeName) {
		TypeMirror type = typeOf(typeName);
		return isAssignableTo(type);
	}

	private boolean isAssignableTo(TypeMirror type) {
		return types.isAssignable(types.erasure(this.type), types.erasure(type));
	}
}
