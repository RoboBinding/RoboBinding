package org.robobinding.codegen.typewrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class TypesWrapper {
	private final Types types;
	private final Elements elements;
	
	public TypesWrapper(Types types, Elements elements) {
		this.types = types;
		this.elements = elements;
	}

	public boolean isSameType(TypeMirror t1, TypeMirror t2) {
		return types.isSameType(types.erasure(t1), types.erasure(t2));
	}

	public boolean isSameType(Class<?> t1, TypeMirror t2) {
		return isSameType(typeMirrorOf(t1), t2);
	}
	
	public boolean isSameType(TypeMirror t1, Class<?> t2) {
		return isSameType(t1, typeMirrorOf(t2));
	}

	private boolean isAssignable(TypeMirror t1, TypeMirror t2) {
		return types.isAssignable(types.erasure(t1), types.erasure(t2));
	}

	public boolean isAssignableTo(TypeMirror t1, Class<?> t2) {
		return isAssignable(t1, typeMirrorOf(t2));
	}

	public boolean isAssignableTo(TypeMirror t1, String t2) {
		return isAssignable(t1, typeMirrorOf(t2));
	}
	
	public TypeMirror typeMirrorOf(Class<?> type) {
		return typeMirrorOf(type.getName());
	}

	public TypeMirror typeMirrorOf(String type) {
		return elements.getTypeElement(type).asType();
	}

	public Element asElement(DeclaredType t) {
		return types.asElement(t);
	}

	public TypeElement boxedClass(PrimitiveType p) {
		return types.boxedClass(p);
	}

}
